package com.qcby.personalmanagement.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class RoleQuery {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限字符
     */
    private String permission;

    /**
     * 角色状态(0停用，1正常)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间起始
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTimeEnd;

    /**
     * 页面索引
     */
    private Integer pageIndex;

    /**
     * 页面大小
     */
    private Integer pageSize;



}
