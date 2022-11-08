package com.hzh.pinda.authority.config;

import com.hzh.pinda.database.datasource.BaseMybatisConfiguration;
import com.hzh.pinda.database.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @NAME: AuthorityMybatisAutoConfiguration
 * @USER: DaHuangGO
 * @DATE: 2022/11/8
 * @TIME: 14:15
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 08
 * Mybatis相关配置
 */
@Configuration
@Slf4j
public class AuthorityMybatisAutoConfiguration extends BaseMybatisConfiguration {
    public AuthorityMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }
}
