package com.hzh.pinda.zuul.filter;

import cn.hutool.core.util.StrUtil;
import com.hzh.pinda.base.R;
import com.hzh.pinda.common.adapter.IgnoreTokenConfig;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * @NAME: BaseFilter
 * @USER: DaHuangGO
 * @DATE: 2022/11/10
 * @TIME: 14:16
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 10
 * 基础过滤器
 */
public abstract class BaseFilter extends ZuulFilter {
    @Value("${server.servlet.context-path}")
    protected String zuulPrefix;

    //判断当前请求的url是否需要忽略（直接放行，不进行jwt或者其他鉴权操作）
    protected boolean isIgnoreToken(){
        //动态获取当前请求的uri
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // /api/file/user/list
        String uri=request.getRequestURI();
        // 截取/file/user/list
        uri=StrUtil.subSuf(uri,zuulPrefix.length());
        //截取/user/list
        uri=StrUtil.subSuf(uri,uri.indexOf("/",1));
        //判断当前的请求路径是否需要被忽略
        boolean ignoreToken = IgnoreTokenConfig.isIgnoreToken(uri);
        return ignoreToken;
    }

    //网关抛异常，不再进行路由，而是直接返回前端
    protected void errorResponse(String errMsg,int errCode,int httpStatusCode){
        RequestContext ctx = RequestContext.getCurrentContext();
        //设置相应状态码
        ctx.setResponseStatusCode(httpStatusCode);
        //设置响应头信息
        ctx.addZuulResponseHeader("Content-Type","application/json;charset=utf-8");
        if (ctx.getResponseBody()==null){
            //设置响应体
            ctx.setResponseBody(R.fail(errCode,errMsg).toString());
            //不进行路由直接返回
            ctx.setSendZuulResponse(false);
        }
    }

}
