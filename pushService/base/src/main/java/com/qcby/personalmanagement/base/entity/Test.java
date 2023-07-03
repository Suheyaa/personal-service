package com.qcby.personalmanagement.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject: pushService
 * @Author: Yuan Haozhe
 * @CreateTime: 2023-07-03  09:47
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@TableName("t_test")
public class Test {
    @TableId(type=IdType.ASSIGN_ID)
    private Long id;

    private String name;
}
