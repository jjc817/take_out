package io.renren.service;

import io.renren.common.page.PageData;
import io.renren.common.service.CrudService;
import io.renren.dto.CouponDTO;
import io.renren.entity.CouponEntity;

import java.util.Map;

public interface CouponService extends CrudService<CouponEntity, CouponDTO> {
    /**
     * 微信小程序用户优惠券查询
     * @param params
     * @return
     */
    PageData<CouponEntity> userPage(Map<String, Object> params);




    CouponEntity getOne(Long id);

    int changeStatus(Long id);

    int cleanExpired();

    CouponEntity submit(Map params);
}
