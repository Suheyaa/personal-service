package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WYX
 * @version 1.0
 * @description: UserANDPost
 * @date 2023/7/10 0:12
 */

@Data
@TableName("sys_user_post")
public class UserPostPO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long sysUserId;

    /**
     * 岗位id
     */
    private Long sysPostId;
}
