package com.hzh;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.base.Captcha;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @NAME: CacptchaTest
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 13:20
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
public class CaptchaTest {
    @Test
    public void t1() throws FileNotFoundException {
        //中文验证码
        Captcha captcha=new ChineseCaptcha(150,60);
        //获取本次生 成的验证码
        String text = captcha.text();
        captcha.out(new FileOutputStream(new File("E://test.png")));
    }


    @Test
    public void t2() throws FileNotFoundException {
        Captcha captcha=new ArithmeticCaptcha();
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        //获取本次生 成的验证码
        String text = captcha.text();
        System.out.println(text);
        //captcha.out(new FileOutputStream(new File("E://test.png")));
    }
}
