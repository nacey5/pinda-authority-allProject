package com.hzh.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hzh.pinda.auth.server.utils.JwtTokenServerUtils;
import com.hzh.pinda.auth.utils.JwtUserInfo;
import com.hzh.pinda.auth.utils.Token;
import com.hzh.pinda.authority.biz.service.auth.ResourceService;
import com.hzh.pinda.authority.biz.service.auth.UserService;
import com.hzh.pinda.authority.dto.auth.LoginDTO;
import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.dto.auth.UserDTO;
import com.hzh.pinda.authority.entity.auth.Resource;
import com.hzh.pinda.authority.entity.auth.User;
import com.hzh.pinda.base.R;
import com.hzh.pinda.common.constant.CacheKey;
import com.hzh.pinda.dozer.DozerUtils;
import com.hzh.pinda.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @NAME: AuthManager
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 15:49
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 * 认证管理器
 */
@Service
@Slf4j
public class AuthManager {

    //用户服务
    @Autowired
    private UserService userService;
    //类型转换对象
    @Autowired
    private DozerUtils dozerUtils;
    //令牌服务
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;
    //权限服务
    @Autowired
    private ResourceService resourceService;
    //缓存服务
    @Autowired
    private CacheChannel cacheChannel;


    /**
     * 登录认证
     * @param account
     * @param password
     * @return
     */
    public R<LoginDTO> login(String account,String password) {
        List<String> permissionList=null;
                R<User> userR = check(account, password);
        Boolean isError = userR.getIsError();
        User user = userR.getData();
        if (isError){
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }

        //生成jwt令牌
        Token token = getUserToken(user);

        List<Resource> userResource = resourceService.findVisibleResource(ResourceQueryDTO.builder().userId(user.getId()).build());
        if (userResource!=null&&userResource.size()>0){
            //将用户对应的权限(前端按钮)使用
            permissionList=userResource.stream().map(Resource::getCode).collect(Collectors.toList());
            //将用户对应的权限(后端网关)进行缓存
            List<String> visibleResource = userResource.stream().map((resource -> {
                return resource.getMethod() + resource.getUrl();
            })).collect(Collectors.toList());
            cacheChannel.set(CacheKey.USER_RESOURCE,user.getId().toString(),visibleResource);
        }

        //封装返回结果

        LoginDTO loginDTO = LoginDTO.builder().
                user(dozerUtils.map(user, UserDTO.class)).
                token(token).
                permissionsList(permissionList).
                build();
        return R.success(loginDTO);
    }

    /**
     * 得到用户的登录令牌
     * @param user
     * @return
     */
    private Token getUserToken(User user) {
        JwtUserInfo jwtUserInfo=new JwtUserInfo(user.getId(),user.getAccount(),user.getName(),user.getOrgId(),user.getStationId());
        return jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);
    }

    /**
     * 检查账号密码
     * @param account
     * @param password
     * @return
     */
    private R<User> check(String account, String password) {
        //校验账号，密码是否正确
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getAccount, account));
        //将前端提交的密码进行加密
        String md5Hex = DigestUtils.md5Hex(password);
        if (user==null||!user.getPassword().equals(md5Hex)){
            return R.fail(ExceptionCode.JWT_USER_INVALID);
        }else {
            return R.success(user);
        }
    }
}
