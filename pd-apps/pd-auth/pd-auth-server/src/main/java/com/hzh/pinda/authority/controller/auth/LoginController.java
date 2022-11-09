package com.hzh.pinda.authority.controller.auth;

import com.hzh.pinda.authority.biz.service.auth.ValidateCodeService;
import com.hzh.pinda.authority.biz.service.auth.impl.AuthManager;
import com.hzh.pinda.authority.dto.auth.LoginDTO;
import com.hzh.pinda.authority.dto.auth.LoginParamDTO;
import com.hzh.pinda.base.BaseController;
import com.hzh.pinda.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @NAME: LoginController
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 13:31
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 * 登录(认证)
 */
@RestController
@RequestMapping("/anno")
@Api(tags = "登录控制器",value = "LoginController")
public class LoginController extends BaseController {

    //验证码管理服务
    @Autowired
    private ValidateCodeService validateCodeService;
    //权限管理对象
    @Autowired
    private AuthManager authManager;


    /**
     * 为前端系统生成验证码
     * @param key 每个用户生成的验证码所对应的key
     */
    @GetMapping(value = "/captcha",produces = "image/png")
    @ApiOperation(notes = "验证码",value = "验证码")
    public void captcha(@RequestParam("key") String key, HttpServletResponse response) throws IOException {
        validateCodeService.create(key,response);
    }

    //登录认证
    @PostMapping("/login")
    @ApiOperation(notes = "登录",value = "登录")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO loginParamDTO){
        //校验验证码是否正确
        boolean isTrue=validateCodeService.check(loginParamDTO.getKey(),loginParamDTO.getCode());
        if (isTrue){//验证码校验通过
            //校验账号，密码是否正确
            R<LoginDTO> r=authManager.login(loginParamDTO.getAccount(),loginParamDTO.getPassword());
            return r;
        }
        //验证码校验不通过,直接返回
        return this.success(null);
    }

    //登录认证
    @PostMapping("/check")
    @ApiOperation(notes = "校验验证码-仅用于测试",value = "校验验证码")
    public boolean check(@RequestBody LoginParamDTO loginParamDTO){
        //校验验证码是否正确
        boolean isTrue=validateCodeService.check(loginParamDTO.getKey(),loginParamDTO.getCode());
        return isTrue;
    }
}
