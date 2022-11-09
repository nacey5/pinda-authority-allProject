package com.hzh.pinda.authority.biz.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.entity.auth.Resource;

import java.util.LinkedList;

/**
 * @NAME: ResourceService
 * @USER: DaHuangGO
 * @DATE: 2022/11/9
 * @TIME: 18:54
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 09
 */
public interface ResourceService extends IService<Resource> {
    public LinkedList<Resource> findVisibleResource(ResourceQueryDTO resourceQueryDTO);
}
