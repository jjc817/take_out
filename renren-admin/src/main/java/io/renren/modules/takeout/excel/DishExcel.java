package io.renren.modules.takeout.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 菜品管理
 *
 * @author David 321740709@qq.com
 * @since 1.0.0 2022-08-17
 */
@Data
public class DishExcel {
    @Excel(name = "主键")
    private Long id;
    @Excel(name = "菜品名称")
    private String name;
    @Excel(name = "菜品分类id")
    private Long categoryId;
    @Excel(name = "菜品价格")
    private BigDecimal price;
    @Excel(name = "商品码")
    private String code;
    @Excel(name = "图片")
    private String image;
    @Excel(name = "描述信息")
    private String description;
    @Excel(name = "0 停售 1 起售")
    private Integer status;
    @Excel(name = "顺序")
    private Integer sort;
    @Excel(name = "创建人")
    private Long creator;
    @Excel(name = "创建时间")
    private Date createDate;
    @Excel(name = "修改人")
    private Long updater;
    @Excel(name = "更新时间")
    private Date updateDate;
    @Excel(name = "是否删除")
    private Integer isDeleted;

}