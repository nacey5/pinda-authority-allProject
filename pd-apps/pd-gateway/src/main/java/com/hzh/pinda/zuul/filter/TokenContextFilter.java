package com.hzh.pinda.zuul.filter;

import com.hzh.pinda.auth.client.properties.AuthClientProperties;
import com.hzh.pinda.auth.client.utils.JwtTokenClientUtils;
import com.hzh.pinda.auth.utils.JwtUserInfo;
import com.hzh.pinda.base.R;
import com.hzh.pinda.context.BaseContextConstants;
import com.hzh.pinda.exception.BizException;
import com.hzh.pinda.utils.StrHelper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @NAME: TokenContextFilter
 * @USER: DaHuangGO
 * @DATE: 2022/11/10
 * @TIME: 14:55
 * @YEAR: 2022
 * @MONTH: 11
 * @DAY: 10
 * 当前过滤器负责解析请求头中的jwt令牌并且将解析出来的用户信息放入zuulheader中
 */
@Component
public class TokenContextFilter extends BaseFilter{
    //权限客户端的配置类
    @Autowired
    private AuthClientProperties authClientProperties;
    //客户端的令牌解析器
    @Autowired
    private JwtTokenClientUtils jwtTokenClientUtils;
    //前置过滤器
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //设置过滤器的过滤顺序，数值越大，优先级越低
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER+1;
    }

    //是否执行当前过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //过滤逻辑
    @Override
    public Object run() throws ZuulException {
        if (isIgnoreToken()){
            //直接放行
            return null;
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //从请求头中获取前端提交的jwt令牌
        String userToken = request.getHeader(authClientProperties.getUser().getHeaderName());
        JwtUserInfo userInfo=null;
        //解析jwt令牌
        try {
            userInfo= jwtTokenClientUtils.getUserInfo(userToken);
        }catch (BizException e){
            errorResponse(e.getMessage(),e.getCode(),200);
            return null;
        }catch (Exception e){
            errorResponse("解析jwt令牌出错", R.FAIL_CODE,200);
            return null;
        }

        //将信息放入header
        if (userInfo != null) {
            addHeader(ctx, BaseContextConstants.JWT_KEY_ACCOUNT,
                    userInfo.getAccount());
            addHeader(ctx, BaseContextConstants.JWT_KEY_USER_ID,
                    userInfo.getUserId());
            addHeader(ctx, BaseContextConstants.JWT_KEY_NAME,
                    userInfo.getName());
            addHeader(ctx, BaseContextConstants.JWT_KEY_ORG_ID,
                    userInfo.getOrgId());
            addHeader(ctx, BaseContextConstants.JWT_KEY_STATION_ID,
                    userInfo.getStationId());
        }
        return null;
    }

    /**
     * 将指定的用户信息放入zuul的header中
     * @param ctx
     * @param name
     * @param value
     */
    private void addHeader(RequestContext ctx, String name, Object value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        ctx.addZuulRequestHeader(name, StrHelper.encode(value.toString()));
    }
}
