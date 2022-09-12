package io.renren.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("coupon")
public class CouponEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 1表示可使用，2表示已使用，3表示已过期
     */
    private Integer status;
    /**
     * 拥有用户
     */
    private Long userId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date expireTime;
    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;
    /**
     * 需凑满金额
     */
    private BigDecimal requiredAmount;
    /**
     * 描述
     */
    private String couponDescribe;

}
