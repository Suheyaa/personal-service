package com.qcby.personalmanagement.web.controller;

import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.Test;
import com.qcby.personalmanagement.base.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;

/**
 * @BelongsProject: pushService
 * @Author: Yuan Haozhe
 * @CreateTime: 2023-07-03  09:41
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private ITestService iTestService;

    @RequestMapping("/add/{name}")
    public Result<String> add(@PathVariable(name = "name")String name){
        Test test=new Test();
        test.setName(name);
        boolean save = iTestService.save(test);
        return new Result<String>(0,save?"成功":"失败","200");
    }

}