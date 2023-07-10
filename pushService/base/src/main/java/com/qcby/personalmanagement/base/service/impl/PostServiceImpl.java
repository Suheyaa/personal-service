package com.qcby.personalmanagement.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.framework.common.enums.DeleteFlagEnum;
import com.qcby.framework.common.exception.ServiceException;
import com.qcby.framework.common.pojo.PageParam;
import com.qcby.framework.common.pojo.PageResult;
import com.qcby.framework.common.pojo.Result;
import com.qcby.personalmanagement.base.entity.dto.PostDTO;
import com.qcby.personalmanagement.base.entity.po.PostPO;
import com.qcby.personalmanagement.base.mapper.PostMapper;
import com.qcby.personalmanagement.base.service.IPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 岗位服务实现
 *
 * @author 38424
 * @date 2023/07/03
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, PostPO> implements IPostService {


    public LambdaQueryWrapper<PostPO> buildWrapper(PostDTO postDTO){
        LambdaQueryWrapper<PostPO> lqw = new LambdaQueryWrapper<PostPO>();
        lqw.eq(PostPO::getDeleteFlag, DeleteFlagEnum.NO_DELETE.getCode());
        lqw.orderByDesc(PostPO::getId);
        return lqw;
    }

    @Override
    public Result<PageResult<PostDTO>> postPageQuery(PostDTO postDTO, PageParam pageParam) {
        LambdaQueryWrapper<PostPO> lqw = buildWrapper(postDTO);
        Page<PostPO> postPOPage = this.baseMapper.selectPage(new Page<>(pageParam.getPageNo(), pageParam.getPageSize()), lqw);
        List<PostDTO> postDTOS = BeanUtil.copyToList(postPOPage.getRecords(),PostDTO.class);
        PageResult pageResult = new PageResult();
        pageResult.setList(postDTOS);
        pageResult.setTotal(postPOPage.getTotal());
        return Result.getSuccessResult(pageResult);
    }

    @Override
    public List<PostDTO> findPost(PostDTO postDTO) {
        PostPO postPO=null;
        List<PostDTO> postDTOS=null;
        if(StrUtil.isBlank(postDTO.getPostCode()) && StrUtil.isBlank(postDTO.getPostName()) && ObjectUtil.isNull(postDTO.getStatus())){
            throw new ServiceException("500","输入为空");
        }
        if(!StrUtil.isBlank(postDTO.getPostCode()) && !StrUtil.isBlank(postDTO.getPostName())){
            postPO=this.baseMapper.selectOne(new LambdaQueryWrapper<PostPO>().eq(PostPO::getPostCode,postDTO.getPostCode()));
            if(!postDTO.getPostName().equals(postPO.getPostName())){
                throw new ServiceException("500","输入岗位编码与岗位名称不对应");
            }
        }
        if(!StrUtil.isBlank(postDTO.getPostCode()) && StrUtil.isBlank(postDTO.getPostName())){
            postPO=this.baseMapper.selectOne(new LambdaQueryWrapper<PostPO>().eq(PostPO::getPostCode,postDTO.getPostCode()));
        }else if(StrUtil.isBlank(postDTO.getPostCode()) && !StrUtil.isBlank(postDTO.getPostName())){
            postPO=this.baseMapper.selectOne(new LambdaQueryWrapper<PostPO>().eq(PostPO::getPostName,postDTO.getPostName()));
        }else if(StrUtil.isBlank(postDTO.getPostCode()) && StrUtil.isBlank(postDTO.getPostName())){
            List<PostPO> postPOS=this.baseMapper.selectList(new LambdaQueryWrapper<PostPO>().eq(PostPO::getStatus,postDTO.getStatus()));
            postDTOS=BeanUtil.copyToList(postPOS,PostDTO.class);
            return postDTOS;
        }
        PostDTO postDTO1=BeanUtil.copyProperties(postPO,PostDTO.class);
        postDTOS.add(postDTO1);
        return postDTOS;
    }

    @Override
    public Boolean changePost(PostDTO postDTO) {
        int temp;
        PostPO postPO=BeanUtil.copyProperties(postDTO,PostPO.class);
        if(ObjectUtil.isNull(postDTO.getId())){
            postPO.setCreateTime(new Date());
            postPO.setCreateBy("admin");
            temp=this.baseMapper.insert(postPO);
        }else{
            postPO.setUpdateTime(new Date());
            postPO.setUpdateBy("admin");
            temp=this.baseMapper.updateById(postPO);
        }
        return temp>0;
    }

    @Override
    public Boolean removePost(List<PostDTO> postDTOS) {
        int temp;
        if(postDTOS.size()==1){
            temp=this.baseMapper.deleteById(postDTOS.get(0).getId());
        }else{
            List<Long> ids=new ArrayList<Long>();
            postDTOS.forEach(it->ids.add(it.getId()));
            temp=this.baseMapper.delete(new QueryWrapper<PostPO>().in("id",ids));
        }
        return temp>0;
    }

}