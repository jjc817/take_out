/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import com.alibaba.fastjson.JSONObject;
import io.renren.annotation.Login;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.LoginDTO;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
@Slf4j
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;




    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);


        //用户登录
        Map<String, Object> map = userService.login(dto);

        return new Result().ok(map);
    }

    @Login
    @PostMapping("/wxGetPhone")
    @ApiOperation("微信小程序获取手机号")
    public Result<JSONObject> wxGetPhone(@RequestBody LoginDTO dto) {

        JSONObject jsonObject = userService.wxGetPhone(dto);

        return new Result().ok(jsonObject);
    }

    @Login
    @PostMapping("/logout")
    @ApiOperation("退出")
    public Result logout(@ApiIgnore @RequestAttribute("userId") Long userId){
        tokenService.expireToken(userId);
        return new Result();
    }

}