package com.qcby.personalmanagement.base.vo;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class DeptVO {
    /**
     * id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 部门状态
     */
    private Integer deptStatus;

    /**
     * 上级部门id
     */
    private Long superiorId;

    /**
     * 顺序
     */
    private Integer orderNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 负责人id
     */
    private Long leaderId;

    /**
     * 负责人
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
