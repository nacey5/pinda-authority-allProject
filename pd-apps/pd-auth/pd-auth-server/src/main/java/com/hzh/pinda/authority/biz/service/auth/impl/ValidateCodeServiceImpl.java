package com.hzh.pinda.authority.biz.service.auth.impl;

import com.hzh.pinda.authority.biz.service.auth.ValidateCodeService;
import com.hzh.pinda.common.constant.CacheKey;
import com.hzh.pinda.exception.BizException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @NAME: ValidateCodeServiceImpl
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 13:40
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    //通过当前对象可以操作缓存
    @Autowired
    private CacheChannel cacheChannel;

    //生成算术验证码，同时需要将验证码进行缓存
    @Override
    public void create(String key, HttpServletResponse response) throws IOException {

        if (StringUtils.isBlank(key)){
            throw BizException.validFail("验证码key不能为空");
        }


        Captcha captcha=new ArithmeticCaptcha();
        //本次产生的验证码
        String value = captcha.text();
        //将验证码进行缓存
        cacheChannel.set(CacheKey.CAPTCHA,key,value);
        //将生成的验证码图片通过输出流写回浏览器页面
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA,"No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL,"No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES,0L);
        captcha.out(response.getOutputStream());
    }

    /**
     * 在region中检验验证码
     * @param key
     * @param code
     * @return
     */
    @Override
    public boolean check(String key, String code) {
        if (StringUtils.isBlank(key)){
            throw BizException.validFail("验证码key不能为空");
        }
        CacheObject cacheObject = cacheChannel.get(CacheKey.CAPTCHA, key);
        if (cacheObject==null) {
            throw BizException.validFail("验证码已过期");
        }
        if (!code.equals((String)cacheObject.getValue())){
            throw BizException.validFail("验证码错误");
        }
        //验证成功后立即清除验证码
        cacheChannel.evict(CacheKey.CAPTCHA,key);
        return true;
    }
}
