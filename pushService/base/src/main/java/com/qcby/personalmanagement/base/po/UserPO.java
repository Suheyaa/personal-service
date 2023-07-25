package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: User
 * @author WYX
 * @date 2023/7/9 16:55
 * @version 1.0
 */
@Data
@TableName("sys_user")
public class UserPO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 手机号
     */
    private String phone;

    /***
     * 密码
     */
    private String password;

    /**
     * 用户状态(0停用，1正常)
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 删除标志（0未删除，1已删除）
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 乐观锁版本号
     */
    private Integer revision;

}
