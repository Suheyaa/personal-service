package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@TableName("sys_dept")
@Data
public class DeptPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 负责人id
     */
    private Integer leaderId;

    /**
     * 部门状态
     */
    private Integer deptStatus;


    /**
     * 上级部门id
     */
    private Integer superiorId;


    /**
     * 祖级列表
     */
    private String ancestors;


    /**
     * 顺序
     */
    private Integer orderNum;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    /**
     * 删除标记
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁
     */
    private Integer revision;



}
