package com.qcby.personalmanagement.base.dto;

import lombok.Data;

@Data
public class DeptDTO {

    /**
     * id
     */
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


}
