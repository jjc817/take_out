/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.log.controller;

import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.modules.log.dto.SysLogLoginDTO;
import io.renren.modules.log.excel.SysLogLoginExcel;
import io.renren.modules.log.service.SysLogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 登录日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@RestController
@RequestMapping("sys/log/login")
@Api(tags="登录日志")
public class SysLogLoginController {
    @Autowired
    private SysLogLoginService sysLogLoginService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = "status", value = "状态  0：失败    1：成功    2：账号已锁定", paramType = "query", dataType="int"),
        @ApiImplicitParam(name = "creatorName", value = "用户名", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:log:login")
    public Result<PageData<SysLogLoginDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<SysLogLoginDTO> page = sysLogLoginService.page(params);

        return new Result<PageData<SysLogLoginDTO>>().ok(page);
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "status", value = "状态  0：失败    1：成功    2：账号已锁定", paramType = "query", dataType="int"),
        @ApiImplicitParam(name = "creatorName", value = "用户名", paramType = "query", dataType="String")
    })
    @RequiresPermissions("sys:log:login")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<SysLogLoginDTO> list = sysLogLoginService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, SysLogLoginExcel.class);
    }

}