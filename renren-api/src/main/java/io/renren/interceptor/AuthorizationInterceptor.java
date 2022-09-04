/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.interceptor;

import io.renren.annotation.Login;
import io.renren.common.exception.ErrorCode;
import io.renren.common.exception.RenException;
import io.renren.entity.TokenEntity;
import io.renren.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    public static final String USER_KEY = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        if(annotation == null){
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //token = "488344b95e884626b83f175895cdd146";
        log.info("token={}",token);
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
            log.info("token={}",token);
        }

//        token为空
        if(StringUtils.isBlank(token)){
            throw new RenException(ErrorCode.TOKEN_NOT_EMPTY);
        }

        //查询token信息
        //有时候明明有token传过来，却查询不到数据信息
        //因为微信小程序端有缓存，要即使清除
        TokenEntity tokenEntity = tokenService.getByToken(token);
//        if(tokenEntity == null || tokenEntity.getExpireDate().getTime() < System.currentTimeMillis()){
//            throw new RenException(ErrorCode.TOKEN_INVALID);
//        }

        //设置userId到request里，后续根据userId，获取用户信息

        request.setAttribute(USER_KEY, tokenEntity.getUserId());
        log.info("userId={}",request.getAttribute("userId"));
        return true;
    }
}
