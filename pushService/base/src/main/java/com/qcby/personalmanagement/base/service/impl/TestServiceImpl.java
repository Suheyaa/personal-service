package com.qcby.personalmanagement.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.personalmanagement.base.entity.Test;
import com.qcby.personalmanagement.base.mapper.TestMapper;
import com.qcby.personalmanagement.base.service.ITestService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: pushService
 * @Author: Yuan Haozhe
 * @CreateTime: 2023-07-03  09:46
 * @Description: TODO
 * @Version: 1.0
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
}
