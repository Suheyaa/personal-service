package com.qcby.personalmanagement.base.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * 岗位DTO
 *
 * @author 38424
 * @date 2023/07/03
 */
@Data
public class PostDTO {
    private Long id;
    private String postCode;
    private String postName;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
}
