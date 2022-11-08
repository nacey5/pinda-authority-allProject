package com.hzh.pinda.authority.config;

import com.hzh.pinda.common.handler.DefaultGlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @NAME: ExceptionConfiguration
 * @USER: DaHuangGO
 * @DATE: 2022/11/8
 * @TIME: 13:51
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 08
 * 权限服务中使用的全局异常处理配置类
 */
@Configuration
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
public class ExceptionConfiguration extends DefaultGlobalExceptionHandler {
}
