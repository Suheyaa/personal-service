package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.framework.common.pojo.PageParam;
import com.qcby.framework.common.pojo.PageResult;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.dto.PostDTO;
import com.qcby.personalmanagement.base.entity.po.PostPO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位服务
 *
 * @author 38424
 * @date 2023/07/03
 */
@Service
public interface IPostService extends IService<PostPO> {

    /**
     * 岗位页面查询
     *
     * @param postDTO   帖子dto
     * @param pageParam 页面参数
     * @return {@link Result}<{@link PageResult}<{@link PostDTO}>>
     */
    Result<PageResult<PostDTO>> postPageQuery(PostDTO postDTO, PageParam pageParam);

    /**
     * 查找岗位
     *
     * @param postDTO 帖子dto
     * @return {@link PostDTO}
     */
    List<PostDTO> findPost(PostDTO postDTO);

    /**
     * 添加或修改岗位
     *
     * @param postDTO 帖子dto
     * @return {@link Boolean}
     */
    Boolean changePost(PostDTO postDTO);

    /**
     * 删除或批量删除岗位
     *
     * @param postDTOS 帖子dto
     * @return {@link Boolean}
     */
    Boolean removePost(List<PostDTO> postDTOS);

}