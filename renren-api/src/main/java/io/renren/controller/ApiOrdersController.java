package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.common.constant.Constant;
import io.renren.common.dto.OrdersDTO;
import io.renren.common.page.PageData;
import io.renren.common.service.OrderDetailService;
import io.renren.common.service.OrdersService;
import io.renren.common.utils.Result;
import io.renren.common.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 订单模块
 *
 * @author David 321740709@qq.com
 * @since 1.0.0 2022-08-17
 */
@RestController
@RequestMapping("api/orders")
@Api(tags="订单模块")
public class ApiOrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserUtils userUtils;



    @Login
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<OrdersDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){

        PageData<OrdersDTO> page = ordersService.userPage(params);

        return new Result<PageData<OrdersDTO>>().ok(page);
    }

    @Login
    @PostMapping("submit")
    @ApiOperation("提交订单")
    public Result submit(@RequestBody OrdersDTO ordersDTO){
        ordersService.submit(ordersDTO);
        return new Result().ok(null);
    }

    @Login
    @PostMapping("again")
    @ApiOperation("再来一单")
    public Result again(@RequestBody OrdersDTO ordersDTO){
        if (ordersDTO.getId() != null){
            ordersService.again(ordersDTO.getId(),userUtils.getUserId());
            return new Result().ok(null);
        }
        return new Result().error("失败!");
    }

    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result deleteOrder(@PathVariable Long id){

        ordersService.deleteOrder(id);
        return new Result().ok(null);
    }

    //查询详情。仿page分页查询但是只查一个，后续可以继续优化为不用page封装
    @GetMapping("/{id}")
    public Result<PageData<OrdersDTO>> getOrderById(@PathVariable Long id){

        List<OrdersDTO> ordersDtoList = ordersService.getOrderById(id);

        PageData<OrdersDTO> page = new PageData<>(ordersDtoList,1);
        return new Result<PageData<OrdersDTO>>().ok(page);
    }

}
