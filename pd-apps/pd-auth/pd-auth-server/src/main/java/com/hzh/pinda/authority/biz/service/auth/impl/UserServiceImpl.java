package com.hzh.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzh.pinda.authority.biz.dao.auth.UserMapper;
import com.hzh.pinda.authority.biz.service.auth.UserService;
import com.hzh.pinda.authority.entity.auth.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @NAME: UserServiceImpl
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 15:56
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
