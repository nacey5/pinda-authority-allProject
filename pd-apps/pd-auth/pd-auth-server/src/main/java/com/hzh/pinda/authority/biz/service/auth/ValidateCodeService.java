package com.hzh.pinda.authority.biz.service.auth;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @NAME: ValidateCodeService
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 13:38
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
public interface ValidateCodeService {

    public void create(String key, HttpServletResponse response) throws IOException;

    boolean check(String key, String code);
}
