package com.qcby.personalmanagement.base.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.base.mapper.UserRoleMapper;
import com.qcby.personalmanagement.base.po.UserRolePO;
import com.qcby.personalmanagement.base.service.IUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WYX
 * @version 1.0
 * @description: UserRole
 * @date 2023/7/10 1:52
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRolePO> implements IUserRoleService {

}
