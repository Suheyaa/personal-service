package com.qcby.personalmanagement.web.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qcby.personalmanagement.base.dto.RoleQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public RoleQuery toRoleQuery(){
        RoleQuery roleQuery = new RoleQuery();
        roleQuery.setPageIndex(pageIndex);
        roleQuery.setPageSize(pageSize);
        roleQuery.setRoleName(roleName);
        roleQuery.setPermission(permission);
        roleQuery.setStatus(status);
        roleQuery.setRemark(remark);
        roleQuery.setCreateTimeStart(createTimeStart);
        roleQuery.setCreateTimeEnd(createTimeEnd);
        return roleQuery;
    }
}
