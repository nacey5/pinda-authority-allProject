package com.hzh.pinda.authority.biz.dao.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.entity.auth.Resource;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

/**
 * @NAME: ResourceMapper
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 18:53
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
@Repository
public interface ResourceMapper extends BaseMapper<Resource> {
    public LinkedList<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
