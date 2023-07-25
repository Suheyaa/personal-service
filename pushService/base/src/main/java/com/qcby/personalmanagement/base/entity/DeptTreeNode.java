package com.qcby.personalmanagement.base.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author WYX
 * @version 1.0
 * @description: TODO
 * @date 2023/7/19 16:59
 */
@Data
public class DeptTreeNode {
    private Long id;
    private String deptName;
    private List<DeptTreeNode> children;
}
