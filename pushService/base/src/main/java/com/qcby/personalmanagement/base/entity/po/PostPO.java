package com.qcby.personalmanagement.base.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;


import java.time.LocalDateTime;


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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;


    @TableLogic
    private Integer deleteFlag;
    private Integer revision;
}
