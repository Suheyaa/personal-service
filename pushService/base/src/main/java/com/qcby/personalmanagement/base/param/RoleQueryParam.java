package com.qcby.personalmanagement.base.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色查询参数
 *
 * @author 张吉祥
 * @date 2023/07/05
 */
@Data
public class RoleQueryParam {

    private static final long serialVersionUID = 1L;
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
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
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
