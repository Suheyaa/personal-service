package com.qcby.personalmanagement.base.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 岗位PO
 *
 * @author 38424
 * @date 2023/07/03
 */
@Data
@TableName("sys_post")
public class PostPO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String postCode;
    private String postName;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    @TableLogic
    private Integer deleteFlag;
    private Integer revision;
}
