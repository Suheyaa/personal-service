package com.qcby.personalmanagement.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author WYX
 * @version 1.0
 * @description: 前端返回的分页查询的条件
 * @date 2023/7/16 15:20
 */
@Data
public class UserQuery {
    /**
     * id
     */
    public Long id;

    /**
     * 部门id
     */
    public String deptId;

    /**
     * 用户名
     */
    public String userName;

    /**
     * 手机号
     */
    public String phone;

    /**
     * 状态:0停用，1正常
     */
    public Integer status;

    /**
     * 创建时间起始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime createTimeStart;

    /**
     * 创建时间结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime createTimeEnd;

    /**
     * 页面索引
     */
    public Integer pageIndex;

    /**
     * 页面大小
     */
    public Integer pageSize;

}
