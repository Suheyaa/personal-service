package com.qcby.personalmanagement.base.vo;



import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WYX
 * @version 1.0
 * @description: TODO
 * @date 2023/7/13 10:37
 */

@Data
public class UserPostRoleVO {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long id;

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
     * 岗位id数组
     */
    private List<Long> postId;


    /**
     * 角色id数组
     */
    private List<Long> roleId;

    /***s
     * 所有岗位角色
     */
//    private HashMap<String, Map> postRole;
    private HashMap<String,List> postRole;
}




