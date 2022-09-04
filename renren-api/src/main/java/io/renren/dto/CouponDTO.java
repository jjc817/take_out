package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "优惠券表")
public class CouponDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 1表示可使用，2表示已使用，3表示已过期
     */
    @ApiModelProperty(value = "1表示可使用，2表示已使用，3表示已过期")
    private Integer status;
    /**
     * 拥有用户
     */
    @ApiModelProperty(value = "拥有用户")
    private Long userId;
    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date expireTime;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal preferentialAmount;
    /**
     * 需凑满金额
     */
    @ApiModelProperty(value = "需凑满金额")
    private BigDecimal requiredAmount;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String couponDescribe;

}
