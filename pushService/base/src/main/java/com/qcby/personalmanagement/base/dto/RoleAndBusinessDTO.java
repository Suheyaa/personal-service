package com.qcby.personalmanagement.base.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleAndBusinessDTO {
    /**
     * id
     */
    public Long id;
    /**
     * 角色名
     */
    public String roleName;
    /**
     * 权限字符
     */
    public String permission;
    /**
     * 状态:0停用，1正常
     */
    public Integer status;
    /**
     * 备注
     */
    public String remark;

    /**
     * 创建日期
     */
    public Date createDate;

    /**
     * 可以使用的业务的id
     */
    public List<Long> ids;
}
