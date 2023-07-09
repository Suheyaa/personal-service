package com.qcby.personalmanagement.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.qcby.framework.common.pojo.PageParam;
import com.qcby.framework.common.pojo.PageResult;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.dto.PostDTO;
import com.qcby.personalmanagement.base.service.IPostService;
import com.qcby.personalmanagement.web.entity.param.PostParam;
import com.qcby.personalmanagement.web.entity.vo.PostVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岗位控制器
 *
 * @author 38424
 * @date 2023/07/03
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private IPostService postService;

    @GetMapping("/page-query")
    public Result<PageResult<PostVO>> pageQuery(PostParam postParam, PageParam pageParam){
        PostDTO postDTO = BeanUtil.copyProperties(postParam,PostDTO.class);
        Result<PageResult<PostDTO>> pageResultResult = postService.postPageQuery(postDTO, pageParam);
        List<PostVO> postVOS = BeanUtil.copyToList(pageResultResult.getData().getList(), PostVO.class);
        PageResult<PostVO> pageResult = new PageResult();
        pageResult.setTotal(pageResultResult.getData().getTotal());
        pageResult.setList(postVOS);
        return Result.getSuccessResult(pageResult);
    }

    @GetMapping("/find-post")
    public Result<List<PostVO>> findPost(PostParam postParam){
        PostDTO postDTO=BeanUtil.copyProperties(postParam,PostDTO.class);
        List<PostDTO> postDTOS=postService.findPost(postDTO);
        List<PostVO> postVOS=BeanUtil.copyToList(postDTOS,PostVO.class);
        return Result.getSuccessResult(postVOS);
    }

    @PostMapping("/change-post")
    public Result<Boolean> changePost(@RequestBody PostParam postParam){
        PostDTO postDTO=BeanUtil.copyProperties(postParam,PostDTO.class);
        return Result.getSuccessResult(postService.changePost(postDTO));
    }

    @PostMapping("/remove-post")
    public Result<Boolean> removePost(@RequestBody List<PostParam> postParams){
        List<PostDTO> postDTOS=BeanUtil.copyToList(postParams,PostDTO.class);
        return Result.getSuccessResult(postService.removePost(postDTOS));
    }

}