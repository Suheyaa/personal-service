package com.qcby.personalmanagement.web.entity.param;

import lombok.Data;

import java.util.Date;

/**
 * 岗位Param
 *
 * @author 38424
 * @date 2023/07/03
 */
@Data
public class PostParam {
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
