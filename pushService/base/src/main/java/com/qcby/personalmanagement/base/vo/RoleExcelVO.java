package com.qcby.personalmanagement.base.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用于导出excel
 *
 * @author 张吉祥
 * @date 2023/07/11
 */
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(25)
@Data
public class RoleExcelVO {
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 角色名称
     */
    @ExcelProperty(value = "角色名称")
    private String roleName;

    /**
     * 权限字符
     */
    @ExcelProperty(value = "权限字符")
    private String permission;

    /**
     * 角色状态(0停用，1正常)
     */
    @ExcelProperty(value = "角色状态")
    private Integer status;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;
    /**
     * 创建时间
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 可用业务id
     */
    private String ids;

}
