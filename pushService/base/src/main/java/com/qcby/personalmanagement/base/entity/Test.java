package com.qcby.personalmanagement.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @BelongsProject: pushService
 * @Author: Yuan Haozhe
 * @CreateTime: 2023-07-03  09:47
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@TableName("test")
public class Test {
    @TableId(type=IdType.ASSIGN_ID)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;
}
