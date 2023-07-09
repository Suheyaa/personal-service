package com.qcby.personalmanagement.base.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_role_business")
public class RoleBusinessPO {
    /**
     * 角色id
     */
    public Long roleId;
    /**
     * 业务
     */
    public Long businessId;
}
