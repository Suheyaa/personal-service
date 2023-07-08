package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_dept")
public class SysDeptPO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁
     */
    private Integer revision;


    /**
     * 用户名
     */
    private String userName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;
}
