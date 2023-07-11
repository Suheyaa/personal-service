package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.PageResult;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.RoleAndBusinessDTO;
import com.qcby.personalmanagement.base.po.RolePO;
import com.qcby.personalmanagement.base.service.IRoleService;
import com.qcby.personalmanagement.base.vo.BusinessVO;
import com.qcby.personalmanagement.base.vo.RoleVO;
import com.qcby.personalmanagement.web.param.RoleQueryParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
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
    public Result<Integer> insertRole(@RequestBody RoleAndBusinessDTO roleAndBusinessDTO) {
        return Result.getSuccessResult(roleService.insertRole(roleAndBusinessDTO));
    }

    /**
     * 批量删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @RequestMapping("/batch_deletion")
    public Result<Integer> batchDeletion(@RequestBody List<Long> ids) {
        return Result.getSuccessResult(roleService.batchDeletion(ids));
    }

    /**
     * 更新角色
     *
     * @param roleAndBusinessDTO 角色dto
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/update")
    public Result<Integer> updateRole(@RequestBody RoleAndBusinessDTO roleAndBusinessDTO) {
        return Result.getSuccessResult(roleService.updateRole(roleAndBusinessDTO));
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/delete")
    public Result<Integer> deleteRole(@RequestBody Long id) {
        return Result.getSuccessResult(roleService.deleteRole(id));
    }

    /**
     * 分页查询
     *
     * @param roleQueryParam 角色查询参数
     * @return {@link Result}<{@link List}<{@link RoleVO}>>
     */
    @RequestMapping("/paging_query")
    public Result<PageResult<RoleVO>> pagingQuery(@RequestBody RoleQueryParam roleQueryParam) {
        return Result.getSuccessResult(roleService.pagingQuery(roleQueryParam.toRoleQuery()));
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

    /**
     * 通过角色id查询业务
     *
     * @param id     id
     * @return {@link Result}<{@link Boolean}>
     */
    @RequestMapping("/query_business_by_roleId")
    public Result<List<Long>> queryBusinessByRoleId(Long  id) {

        return Result.getSuccessResult(roleService.queryBusinessByRoleId(id));
    }

    /**
     * 得到总数
     *
     * @return {@link Result}<{@link Integer}>
     */
    @RequestMapping("/get_counts")
    public Result<Integer> getCounts() {
        return Result.getSuccessResult(roleService.count());
    }
    @RequestMapping("/list")
    public Result<List<RoleVO>> list() {
        return Result.getSuccessResult(roleService.listRole());
    }
    /**
     * 出口
     * 暂未完全实现
     * @return {@link Result}<{@link List}<{@link BusinessVO}>>
     */
    @RequestMapping("/export")
    public void export(@RequestBody List<Long> ids, HttpServletResponse response) throws IOException {
        System.out.println(ids);
        String filePath = roleService.export(ids);
        System.out.println(filePath);
        try {
            //输入流，通过输入流将读取文件内容
            FileInputStream fileInputStream = new FileInputStream(filePath);
            //输出流，通过输出流将文件回写到浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=角色表.xlsx");
            response.setStatus(200);
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.flush();
            //关闭流
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
