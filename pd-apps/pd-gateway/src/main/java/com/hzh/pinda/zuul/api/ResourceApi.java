package com.hzh.pinda.zuul.api;

import com.hzh.pinda.authority.dto.auth.ResourceQueryDTO;
import com.hzh.pinda.authority.entity.auth.Resource;
import com.hzh.pinda.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @NAME: ResourceApi
 * @USER: DaHuangGO
 * @DATE: 2022/11/10
 * @TIME: 14:09
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 10
 */
@FeignClient(name = "${pinda.feign.authority-server:pd-auth-server}",
        fallback = ResourceApiFallback.class)
public interface ResourceApi {
    //获取所有需要鉴权的资源
    @GetMapping("/resource/list")
    public R<List> list();

    //查询当前登录用户拥有的资源权限
    @GetMapping("/resource")
    public R<List<Resource>> visible(ResourceQueryDTO resource);
}
