package com.hzh.pinda.zuul.api;

import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.entity.auth.Resource;
import com.hzh.pinda.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @NAME: ResourceApiFallback
 * @USER: DaHuangGO
 * @DATE: 2022/11/10
 * @TIME: 14:12
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 10
 * 资源api熔断了
 */
@Component
public class ResourceApiFallback implements ResourceApi{
    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        return null;
    }
}
