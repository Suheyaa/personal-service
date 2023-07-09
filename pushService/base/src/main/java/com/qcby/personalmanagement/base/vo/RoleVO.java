package com.qcby.personalmanagement.base.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleVO {
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
    @ExcelIgnore
    private List<Long> ids;

}
