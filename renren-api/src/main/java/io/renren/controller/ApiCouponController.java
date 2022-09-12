package io.renren.controller;

import cn.hutool.core.date.DateUtil;
import io.renren.annotation.Login;
import io.renren.common.constant.Constant;
import io.renren.common.entity.UserEntity;
import io.renren.common.page.PageData;
import io.renren.common.utils.Result;
import io.renren.common.utils.UserUtils;
import io.renren.common.entity.CouponEntity;
import io.renren.common.service.CouponService;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Map;

/**
 * 优惠券接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api/coupon")
@Api(tags="登录接口")
@Slf4j
public class ApiCouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private UserService userService;


    @Login
    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    public Result<PageData<CouponEntity>> page(@ApiIgnore @RequestParam Map<String, Object> params){

        PageData<CouponEntity> page = couponService.userPage(params);

        return new Result<PageData<CouponEntity>>().ok(page);
    }

    /**
     * 获取单个优惠券信息
     */
    @GetMapping("/{id}")
    public Result<CouponEntity> getCouponById(@PathVariable Long id){
        CouponEntity CouponEntity = couponService.getOne(id);
        return new Result<CouponEntity>().ok(CouponEntity);
    }

    @PutMapping("/change/{id}")
    public Result changeStatus(@PathVariable Long id){
        if (couponService.changeStatus(id)>0) {
            return new Result().ok(null);
        }
        else {
            return new Result().error("修改优惠券为已使用失败");
        }
    }

    @DeleteMapping("/clean")
    public Result cleanExpired(){
        if (couponService.cleanExpired()>0){
            return new Result().ok("成功删除过期优惠券");
        }
        else {
            return new Result().ok(null);
        }
    }

    @PostMapping("/submit")
    public Result<CouponEntity> submit(@RequestBody Map params){
        String userId = (String)params.get("userId");
        long id = Long.parseLong(userId);
        UserEntity userEntity = userService.selectById(id);
        Integer isGetCoupon = userEntity.getIsGetCoupon();
        Date getCouponDay = userEntity.getGetCouponDay();
        Date today = DateUtil.parse(DateUtil.today()).toJdkDate();
        boolean before;
        if (getCouponDay == null){
            before = true;
        }
        else {
            before = getCouponDay.before(today);
        }
        if (isGetCoupon == 0 || before) {

            CouponEntity couponEntity = couponService.submit(params);
            if (couponEntity != null) {
                userService.resetCoupon(id);
                return new Result<CouponEntity>().ok(couponEntity);
            }
            else {
                return new Result().error("生成优惠券失败");
            }

        }
        else {

            return new Result().error("今日已获取红包");
        }
    }
}
