package com.hzh.pinda.authority.biz.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzh.pinda.authority.biz.dao.auth.ResourceMapper;
import com.hzh.pinda.authority.biz.service.auth.ResourceService;
import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.entity.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * @NAME: ResourceServiceImpl
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 18:54
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
@Service
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {
    @Override
    public LinkedList<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO) {
        return baseMapper.findVisibleResource(resourceQueryDTO);
    }
}
