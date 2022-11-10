package com.hzh.pinda.authority.config;

import com.hzh.pinda.authority.biz.service.common.OptLogService;
import com.hzh.pinda.log.entity.OptLogDTO;
import com.hzh.pinda.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.function.Consumer;

/**
 * @NAME: SysLogConfiguration
 * @USER: DaHuangGO
 * @DATE: 2022/11/10
 * @TIME: 13:20
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 10
 * 系统操作日志的配置类
 */
@Configuration
@EnableAsync
public class SysLogConfiguration {
    //创建日志记录的监听器
    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService){
        Consumer<OptLogDTO> consumer=(optLog)->optLogService.save(optLog);
        return new SysLogListener(consumer);
    }
}
