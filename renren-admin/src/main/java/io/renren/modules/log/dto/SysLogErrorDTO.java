/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.log.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 异常日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
@Data
@ApiModel(value = "异常日志")
public class SysLogErrorDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;
	@ApiModelProperty(value = "请求URI")
	private String requestUri;
	@ApiModelProperty(value = "请求方式")
	private String requestMethod;
	@ApiModelProperty(value = "请求参数")
	private String requestParams;
	@ApiModelProperty(value = "用户代理")
	private String userAgent;
	@ApiModelProperty(value = "操作IP")
	private String ip;
	@ApiModelProperty(value = "异常信息")
	private String errorInfo;
	@ApiModelProperty(value = "创建时间")
	private Date createDate;

}