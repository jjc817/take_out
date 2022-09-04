package io.renren.common.service;

import io.renren.common.dto.OrderDetailDTO;

import io.renren.common.entity.OrderDetailEntity;


import java.util.List;

/**
 * 订单明细表
 *
 * @author David 321740709@qq.com
 * @since 1.0.0 2022-08-17
 */
public interface OrderDetailService extends CrudService<OrderDetailEntity, OrderDetailDTO> {

    List<OrderDetailEntity> getByOrderId(Long id);


}