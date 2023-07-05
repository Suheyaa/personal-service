package com.qcby.personalmanagement.base.dto;

import lombok.Data;

@Data
public class BusinessDTO {
    /**
     * id
     */
    public Long id;
    /**
     * 是否可用#0:禁用#1:启用
     */
    public Integer status;
    /**
     * 系统名称
     */
    public String businessName;
    /**
     * 权限字符
     */
    public String permission;
    /**
     * 路由地址
     */
    public String path;
    /**
     * 图标
     */
    public String icon;
}
