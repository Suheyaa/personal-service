package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.PageResult;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.dto.UserPostRoleDTO;
import com.qcby.personalmanagement.base.dto.UserQuery;
import com.qcby.personalmanagement.base.service.IUserService;
import com.qcby.personalmanagement.base.vo.UserPostRoleVO;
import com.qcby.personalmanagement.base.vo.UserVO;
import com.qcby.personalmanagement.web.param.UserDeleteParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author WYX
 * @version 1.0
 * @description: User
 * @date 2023/7/9 17:52
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    IUserService userService;

    /***
     * 新增
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody UserPostRoleDTO userPostRoleDTO) {
        return Result.getSuccessResult(userService.add(userPostRoleDTO));
    }
    /**
     * 删除/批量删除
     */
    @PostMapping("/delete")
    public Result<Integer> delete(@RequestBody UserDeleteParam userDeleteParam) {
//        log.info("ids:{}",userDeleteParam.getIds());
        return Result.getSuccessResult(userService.delete(userDeleteParam.getIds()));
    }

    /**
     * 单条修改
     */
    @PostMapping("/update")
    public Result<Integer> update(@RequestBody UserPostRoleDTO userPostRoleDTO) {
        return Result.getSuccessResult(userService.update(userPostRoleDTO));
    }

    /**
     * 状态更换
     */
    @PostMapping("/status-change")
    public Result<Boolean> statusChange(@RequestBody UserQuery userQuery) {
        return Result.getSuccessResult(userService.statusChange(userQuery));
    }

    /**
     * 岗位和角色选择（对应关系）
     */
//    @RequestMapping("/choose")
//    public Result<UserPostRoleVO> choose(@RequestParam(required = false) Long id){
//        return Result.getSuccessResult(userService.choose(id));
//    }
    @PostMapping("/choose")
    public Result<UserPostRoleVO> choose(@RequestBody(required = false) UserQuery userQuery){
        return Result.getSuccessResult(userService.choose(userQuery));
    }

    /**
     * 分页查询
     */
    @PostMapping("/search")
    public Result<PageResult<UserVO>> search(@RequestBody UserQuery userQuery){
        return Result.getSuccessResult(userService.search(userQuery));
    }

    /**
     * 导入
     */
    @PostMapping("/import")
    public Result<String> importUser(MultipartFile file){
        return Result.getSuccessResult(userService.importUser(file).equals(Boolean.TRUE) ? "导入成功" : "导入失败");
    }
    /**
     * 下载导入导出的excel
     * 导出
     * @return
     */
    @RequestMapping("/export")
    public void exportUser(HttpServletResponse response){
        userService.exportUser(response);
    }

    @RequestMapping("/downloadtemplate")
    public void downloadTemplate(HttpServletResponse response){
        userService.downloadTemplate(response);
    }
    /**
     * 获取部门树列表
     */
    @GetMapping("/userTree")
    public Result<List> deptTree()
    {
        return Result.getSuccessResult(userService.deptTree());
    }


}
