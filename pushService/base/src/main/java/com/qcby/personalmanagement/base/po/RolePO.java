package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 张吉祥
 * @description role`
 * create table `role
 * @date 2023-06-30
 */
@Data
@TableName("sys_role")
public class RolePO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 删除标志（0未删除，1已删除）
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁版本号
     */
    private Integer revision;

}