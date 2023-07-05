package com.qcby.personalmanagement.base.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoleVO {
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 可用业务id
     */
    private List<Long> ids;

}
