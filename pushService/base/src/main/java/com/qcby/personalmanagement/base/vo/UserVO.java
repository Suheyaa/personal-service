package com.qcby.personalmanagement.base.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author WYX
 * @version 1.0
 * @description: User
 * @date 2023/7/9 19:09
 */

@Data
public class UserVO {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @ExcelProperty(value = "id")
    @TableId(type = IdType.ASSIGN_ID)
    @ExcelIgnore
    private Long id;

    /**
     * 用户名称
     */
    @ExcelProperty(value = "用户名称")
    private String userName;

    /**
     * 用户昵称
     */
    @ExcelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 部门id
     */
    @ExcelIgnore
    @ExcelProperty(value = "部门id")
    private Integer deptId;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;
    /**
     * 部门负责人名称
     */
    @ExcelIgnore
    @ExcelProperty(value = "负责人名称")
    private String leader;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /***
     * 密码
     */
    @ExcelProperty(value = "密码")
    private String password;

    /**
     * 用户状态(0停用，1正常)
     */
    @ExcelProperty(value = "用户状态")
    private Integer status;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private Integer sex;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 岗位id数组
     */
    @ExcelIgnore
    private String postId;

    /**
     * 角色id数组
     */
    @ExcelIgnore
    private String roleId;

    @ExcelIgnore
    private Map<String,String> dept;

    /**
     * excel传过来的岗位id字符串
     */
    @ExcelProperty(value = "岗位名称数组")
    public String postName;

    /**
     * excel传过来的角色id字符串
     */
    @ExcelProperty(value = "角色名称数组")
    public String roleName;

}
