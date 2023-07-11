package com.qcby.personalmanagement.base.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.Data;

import java.time.LocalDateTime;
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(25)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment =  VerticalAlignmentEnum.CENTER)
@Data
public class BusinessExcelVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "id")

    private Long id;

    /**
     * 是否可用#0:禁用#1:启用
     */
    @ExcelProperty(value = "是否可用")
    private Integer status;

    /**
     * 系统名称
     */
    @ExcelProperty(value = "系统名称")
    private String businessName;

    /**
     * 权限字符
     */
    @ExcelProperty(value = "权限字符")
    private String permission;

    /**
     * 路由地址
     */
    @ExcelProperty(value = "路由地址")
    private String path;

    /**
     * 图标
     */
    @ExcelProperty(value = "图标")
    private String icon;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
