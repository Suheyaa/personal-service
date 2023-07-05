package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.base.param.RoleQueryParam;
import com.qcby.personalmanagement.base.service.IRoleService;
import com.qcby.personalmanagement.base.vo.RoleVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    IRoleService roleService;

    /**
     * 插入
     *
     * @param roleAndBusinessDTO 角色dto
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/insert")
    public Result<Integer> insertRole(RoleAndBusinessDTO roleAndBusinessDTO) {
        return Result.getSuccessResult(roleService.insertRole(roleAndBusinessDTO));
    }

    /**
     * 批量删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @RequestMapping("/batch_deletion")
    public Result<Integer> batchDeletion(List<Long> ids) {
        return Result.getSuccessResult(roleService.batchDeletion(ids));
    }

    /**
     * 更新角色
     *
     * @param roleAndBusinessDTO 角色dto
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/update")
    public Result<Integer> updateRole(RoleAndBusinessDTO roleAndBusinessDTO) {
        return Result.getSuccessResult(roleService.updateRole(roleAndBusinessDTO));
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/delete")
    public Result<Integer> deleteRole(Long id) {
        return Result.getSuccessResult(roleService.deleteRole(id));
    }

    /**
     * 分页查询
     *
     * @param roleQueryParam 角色查询参数
     * @return {@link Result}<{@link List}<{@link RoleVO}>>
     */
    @RequestMapping("/paging_query")
    public Result<List<RoleVO>> pagingQuery(RoleQueryParam roleQueryParam) {
        return Result.getSuccessResult(roleService.pagingQuery(roleQueryParam));
    }


    /**
     * 改变状态
     *
     * @param id     id
     * @param status 状态
     * @return {@link Result}<{@link Boolean}>
     */
    @RequestMapping("/change_status")
    public Result<Boolean> changeStatus(Long  id, Integer status) {

        return Result.getSuccessResult(roleService.changeStatus(id, status));
    }

}
