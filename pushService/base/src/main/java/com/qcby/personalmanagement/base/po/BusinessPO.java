package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 张吉祥
 * @description business`
 * create table `business
 * @date 2023-06-30
 */
@Data
@TableName("sys_business")
public class BusinessPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 是否可用#0:禁用#1:启用
     */
    private Integer status;

    /**
     * 系统名称
     */
    private String businessName;

    /**
     * 权限字符
     */
    private String permission;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

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
     * 删除标识#0：未删除，1：删除
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁版本号
     */
    private Integer revision;
}