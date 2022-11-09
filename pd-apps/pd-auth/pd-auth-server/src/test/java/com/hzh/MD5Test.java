package com.hzh;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @NAME: MD5Test
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 16:20
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */

public class MD5Test {
    @Test
    public void testMd5(){
        String password="123";
        String md5Hex = DigestUtils.md5Hex(password);
        System.out.println(md5Hex);
    }
}
