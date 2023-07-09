package com.qcby.personalmanagement.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qcby.personalmanagement.base.entity.po.PostPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 岗位映射器
 *
 * @author 38424
 * @date 2023/07/03
 */
@Mapper
public interface PostMapper extends BaseMapper<PostPO> {

}