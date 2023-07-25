package com.qcby.personalmanagement.base.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcby.framework.common.pojo.PageResult;
import com.qcby.personalmanagement.base.dto.UserDTO;
import com.qcby.personalmanagement.base.dto.UserPostRoleDTO;
import com.qcby.personalmanagement.base.dto.UserQuery;
import com.qcby.personalmanagement.base.entity.DeptTreeNode;
import com.qcby.personalmanagement.base.entity.po.PostPO;
import com.qcby.personalmanagement.base.mapper.*;
import com.qcby.personalmanagement.base.po.*;
import com.qcby.personalmanagement.base.service.*;
import com.qcby.personalmanagement.base.vo.UserPostRoleVO;
import com.qcby.personalmanagement.base.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static com.alibaba.excel.read.listener.PageReadListener.BATCH_COUNT;

/**
 * @author WYX
 * @version 1.0
 * @description: User
 * @date 2023/7/9 19:01
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements IUserService {
    @Resource
    private IUserPostService userPostService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private PostMapper postMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private DeptMapper deptMapper;

    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private UserPostMapper userPostMapper;

    @Override
    public Integer add(UserPostRoleDTO userPostRoleDTO){
        UserPO userPO = new UserPO();
        userPO.setUserName(userPostRoleDTO.getUserName());
        userPO.setNickName(userPostRoleDTO.getNickName());
        userPO.setDeptId(userPostRoleDTO.getDeptId());
        userPO.setPassword(userPostRoleDTO.getPassword());
        userPO.setPhone(userPostRoleDTO.getPhone());
        userPO.setStatus(userPostRoleDTO.getStatus());
        userPO.setEmail(userPostRoleDTO.getEmail());
        userPO.setSex(userPostRoleDTO.getSex());
        userPO.setRemark(userPostRoleDTO.getRemark());
        boolean saveUser = this.save(userPO);
        int a = saveUser?1:0;
        // 得到雪花算法产生的id在sys_user_post表和sys_user_roles表中插入数据
        Long userId = userPO.getId();
        int aaa = insertPostRole(userPostRoleDTO,userId);
        return (a==1) && (aaa==1)? 1 : 0;
    }

    /**
     *  将岗位id数组和用户id数组中的数据存到用户和岗位sys_user_post、用户和角色sys_user_role的对应表中
     */

    @Override
    public int insertPostRole(UserPostRoleDTO userPostRoleDTO, Long userId){
        // 在sys_user_post表插入数据
        //      转为userPostPO用于插入
        List<UserPostPO> collectPost = userPostRoleDTO.getPostId().stream().map(e -> {
            UserPostPO userPostPO = new UserPostPO();
            userPostPO.setSysUserId(userId);
            userPostPO.setSysPostId(e);
            return userPostPO;
        }).collect(Collectors.toList());
        //批量插入post

        boolean saveUserPost = userPostService.saveBatch(collectPost);
        // 在sys_user_roles表插入数据
        //      转为userRolePO用于插入
        List<UserRolePO> collectRole = userPostRoleDTO.getRoleId().stream().map(e -> {
            UserRolePO userRolePO = new UserRolePO();
            userRolePO.setSysUserId(userId);
            userRolePO.setSysRoleId(e);
            return userRolePO;
        }).collect(Collectors.toList());
        //批量插入role
        boolean saveUserRole = userRoleService.saveBatch(collectRole);
        return  saveUserPost && saveUserRole ? 1 : 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delete(List<Long> ids) {
        boolean removeUser = this.removeByIds(ids);
        Boolean removeUserPost = userPostService.removeByIds(ids);
        Boolean removeUserRole = userRoleService.removeByIds(ids);
        return removeUser && removeUserPost && removeUserRole? 1 : 0;
    }

    @Override
    public Integer update(UserPostRoleDTO userPostRoleDTO) {
        Long userId = userPostRoleDTO.getId();
        UserPO userPO = new UserPO();
        userPO.setId(userPostRoleDTO.getId());
        userPO.setUserName(userPostRoleDTO.getUserName());
        userPO.setNickName(userPostRoleDTO.getNickName());
        userPO.setDeptId(userPostRoleDTO.getDeptId());
        userPO.setPhone(userPostRoleDTO.getPhone());
        userPO.setStatus(userPostRoleDTO.getStatus());
        userPO.setEmail(userPostRoleDTO.getEmail());
        userPO.setSex(userPostRoleDTO.getSex());
        userPO.setRemark(userPostRoleDTO.getRemark());
        int updateRole = this.getBaseMapper().updateById(userPO);
        //如果没有传岗位id数组，不用更新user_post表
        if (userPostRoleDTO.getPostId().isEmpty() || userPostRoleDTO.getPostId() == null) {
            return updateRole;
        }
        //如果没有传角色id数组，不用更新user_roles表
        if (userPostRoleDTO.getRoleId().isEmpty() || userPostRoleDTO.getRoleId() == null) {
            return updateRole;
        }
        userPostService.removeByIds(Arrays.asList(userId));
        userRoleService.removeByIds(Arrays.asList(userId));
        return updateRole > 0 && (insertPostRole(userPostRoleDTO,userId)==1) ? 1 : 0;
    }

    @Override
    public Boolean statusChange(UserQuery userQuery) {
        UserDTO userDTO = BeanUtil.copyProperties(userQuery,UserDTO.class);
        log.info("id:{},status:{}",userDTO.getId(),userDTO.getStatus());
        UserPO userPO = new UserPO();
        userPO.setId(userDTO.getId());
        userPO.setStatus(userDTO.getStatus());
        return this.updateById(userPO);
    }

    @Override
    public UserPostRoleVO choose(UserQuery userQuery) {

        UserPostRoleVO userPostRoleVO = new UserPostRoleVO();
        HashMap<String,List> postRole = new HashMap<>();


        //查询出所有的post放入hashmap中
        List<PostPO> postPOS = postMapper.selectList(new LambdaQueryWrapper<PostPO>()
                .select(PostPO::getId,PostPO::getPostName));
        System.out.println("postPOS"+postPOS);
        postRole.put("post",postPOS);

        //查询出所有的role放入hashmap中
        List<RolePO> rolePOS = roleMapper.selectList(new LambdaQueryWrapper<RolePO>()
                .select(RolePO::getId,RolePO::getRoleName));

        postRole.put("role",rolePOS);
        //将hashmap放入userPostRoleVO
        userPostRoleVO.setPostRole(postRole);
        //判断有没有传过来id
        if( ObjectUtil.isNull(userQuery)) {
            return userPostRoleVO;
        } else
        {
            //将id对应的user传给userPostVO
            UserPO userPO = new UserPO();
            userPO.setId(userQuery.getId());
            UserPO chooseUserPO = this.getById(userPO);
            BeanUtils.copyProperties(chooseUserPO, userPostRoleVO);
            //查询岗位id数组
            List<Long> userPostId = new ArrayList();
            List<UserPostPO> userPostPOS = userPostService.getBaseMapper().selectList(
                    new LambdaQueryWrapper<UserPostPO>().eq(UserPostPO::getSysUserId,userQuery.getId())
                            .orderByAsc(UserPostPO::getSysPostId));
            for(UserPostPO userPostPO:userPostPOS){
                userPostId.add(userPostPO.getSysPostId());
            }
            userPostRoleVO.setPostId(userPostId);
            //查询角色id数组
            List<Long> userRoleId = new ArrayList();
            List<UserRolePO> userRolePOS = userRoleService.getBaseMapper().selectList(
                    new LambdaQueryWrapper<UserRolePO>().eq(UserRolePO::getSysUserId,userQuery.getId())
                            .orderByAsc(UserRolePO::getSysRoleId));
            for(UserRolePO userRolePO:userRolePOS){
                userRoleId.add(userRolePO.getSysRoleId());
            }
            userPostRoleVO.setRoleId(userRoleId);
        }
        return userPostRoleVO;
    }

    @Override
    public Boolean importUser(MultipartFile file) {

        ArrayList<Object> list = new ArrayList<>();
        AnalysisEventListener listener = new AnalysisEventListener() {

            @Override
            public void invoke(Object data, AnalysisContext context) {
                //获取到每一行数据，逐行进行处理
                list.add(data);
                if (list.size() >= 1){
                    saveData();
                }
                if (list.size() >= BATCH_COUNT) {
                    // 存储完成清理 list
                    list.clear();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                log.info("导入数据完毕");
            }
            private void saveData() {
                //将每次list里面新添加进来的数据保存到数据库中
                for(Object listAdd:list){
                    UserVO userVO = (UserVO) listAdd;
                    //由deptName得到deptId
                    if(BeanUtil.isNotEmpty(userVO.getDeptName())){
                        Long deptId = deptMapper.selectOne(new LambdaQueryWrapper<DeptPO>()
                                .eq(DeptPO::getDeptName,userVO.getDeptName())).getId();
                        userVO.setDeptId(Math.toIntExact(deptId));
                    }
                    UserMapper baseMapper = UserServiceImpl.this.baseMapper;
                    UserPO userPO = BeanUtil.copyProperties(userVO,UserPO.class);
                    baseMapper.insert(userPO);
                    userVO.setId(userPO.getId());
                    //将postId存进user-post表中
                    if(BeanUtil.isNotEmpty(userVO.getPostName())){
                        String[] name = userVO.getPostName().split(",");
                        List<Long> postIds = postMapper.selectList(new LambdaQueryWrapper<PostPO>()
                                        .select(PostPO::getId)
                                        .in(PostPO::getPostName,name))
                                    .stream().map(PostPO::getId).collect(Collectors.toList());
                        List<UserPostPO> userPostPOS = new ArrayList<>();
                        UserPostPO userPostPO = new UserPostPO();
                        for(Long postId:postIds){
                            userPostPO.setSysUserId(userVO.getId());
                            userPostPO.setSysPostId(postId);
                            userPostPOS.add(userPostPO);
                        }
                        userPostService.saveBatch(userPostPOS);
                    }

                    //将roleId存进user-role表中
                    if(BeanUtil.isNotEmpty(userVO.getRoleName())){
                        String[] name = userVO.getRoleName().split(",");
                        List<Long> roleIds = roleMapper.selectList(new LambdaQueryWrapper<RolePO>()
                                        .select(RolePO::getId)
                                        .in(RolePO::getRoleName,name))
                                .stream().map(RolePO::getId).collect(Collectors.toList());
                        List<UserRolePO> userRolePOS = new ArrayList<>();
                        UserRolePO userRolePO = new UserRolePO();
                        for(Long postId:roleIds){
                            userRolePO.setSysUserId(userVO.getId());
                            userRolePO.setSysRoleId(postId);
                            userRolePOS.add(userRolePO);
                        }
                        userRoleService.saveBatch(userRolePOS);
                    }

                }
            }
        };
        if (file.isEmpty()) {
            return Boolean.FALSE;
        }

        try {
            EasyExcel.read(file.getInputStream(), UserVO.class, listener)
                    .sheet(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("导入出错：{}", e.getMessage());
        }
        return Boolean.TRUE;

       }


    @Override
    public void exportUser(HttpServletResponse response) {
        List<Long> ids = new ArrayList<>();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("用户表", "UTF-8");
            //.replaceAll("\+", "20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<UserVO> userList = new ArrayList<>();
            List<UserPO> userPOList = this.baseMapper.selectList(new LambdaQueryWrapper<UserPO>()
                    .like(UserPO::getId,""));
            for(UserPO userPO:userPOList){
                ids.add(userPO.getId());
            }

            // 根据查询条件获取结果集
            List<UserPostRoleDTO> userPostRoleDTOS =  findUsers(ids);
            log.info("userPostRoleDTOS"+userPostRoleDTOS);
            log.info(String.valueOf(userPostRoleDTOS));
            UserDTO userDTO = new UserDTO();
            for(UserPostRoleDTO userPostRoleDTO:userPostRoleDTOS){
                userDTO = BeanUtil.copyProperties(userPostRoleDTO,UserDTO.class);
                log.info("userDTO"+userDTO);
                String postName = "";
                String roleName = "";
                List<String>  postNames = new ArrayList<>();
                List<String>  roleNames = new ArrayList<>();
                for(Long aaa:userPostRoleDTO.getPostId()){
                    postNames = postMapper.selectList(new LambdaQueryWrapper<PostPO>()
                                    .select(PostPO::getPostName)
                                    .in(PostPO::getId,userPostRoleDTO.getPostId()))
                                    .stream().map(PostPO::getPostName).collect(Collectors.toList());
                }
                for(String name:postNames){
                    postName +=name;
                }
                for(Long bbb:userPostRoleDTO.getRoleId()){
                    roleNames = roleMapper.selectList(new LambdaQueryWrapper<RolePO>()
                                    .select(RolePO::getRoleName)
                                    .in(RolePO::getId,userPostRoleDTO.getRoleId()))
                            .stream().map(RolePO::getRoleName).collect(Collectors.toList());
                }
                for(String name:roleNames){
                    roleName +=name;
                }
                userDTO.setPostName(postName);
                userDTO.setRoleName(roleName);
                UserVO userVOS1 = this.baseMapper.selectDeptPro(Long.valueOf(userDTO.getDeptId()));
                UserVO userVO = BeanUtil.copyProperties(userDTO,UserVO.class);
                if(userVOS1!=null){
                    userVO.setDeptName(userVOS1.getDeptName());
                }

                userList.add(userVO);

            }

            //导出excel
            EasyExcel.write(response.getOutputStream(),UserVO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("用户表")
                    .doWrite(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void downloadTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("用户表模板", "UTF-8");
            //.replaceAll("\+", "20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            List<UserVO> userList = new ArrayList<>();

            UserVO userVOEmpty = new UserVO();
            log.info("无导出数据");
            userList.add(userVOEmpty);

            //导出excel
            EasyExcel.write(response.getOutputStream(),UserVO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("用户表模板")
                    .doWrite(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过id数组查找UserRoleDTO数组
     */
    private List<UserPostRoleDTO> findUsers(List<Long> ids) {
        UserPO userPO = new UserPO();
        ArrayList<UserPostRoleDTO> userPostRoleDTOS = new ArrayList<>();
        UserPostRoleDTO userPostRoleDTO = new UserPostRoleDTO();
        if(ids.isEmpty()){
            return null;
        }
        /**
         * todo:将for循环查询数据库改成.in
         */
        for(Long id:ids){
            userPO = this.baseMapper.selectById(id);
            UserPostRoleDTO userPostRoleDTO1 = BeanUtil.copyProperties(userPO,UserPostRoleDTO.class);

            //查询出一个id的岗位数组并放入UserPostRoleDTO中
            List<UserPostPO> userPostPOS = userPostMapper.selectList(new LambdaQueryWrapper<UserPostPO>()
                    .eq(UserPostPO::getSysUserId, id));
            List<Long> collectPost = userPostPOS.stream().map(UserPostPO::getSysPostId).collect(Collectors.toList());
            userPostRoleDTO1.setPostId(collectPost);

            //查询出一个id的角色数组并放入UserPostRoleDTO中
            List<UserRolePO> userRolePOS = userRoleMapper.selectList(new LambdaQueryWrapper<UserRolePO>()
                    .eq(UserRolePO::getSysUserId, id));
            List<Long> collectRole = userRolePOS.stream().map(UserRolePO::getSysRoleId).collect(Collectors.toList());
            userPostRoleDTO1.setRoleId(collectRole);
            userPostRoleDTOS.add(userPostRoleDTO1);
        }

        return userPostRoleDTOS;
    }

    @Override
    public PageResult<UserVO> search(UserQuery userQuery) {
        ArrayList<UserVO> userVOS = new ArrayList<>();
        List<UserPO> userPOS = new ArrayList<>();
        LambdaQueryWrapper<UserPO> wrpper = new LambdaQueryWrapper<>();
        if(BeanUtil.isNotEmpty(userQuery.getDeptId())){
            List<DeptPO> deptPOS = this.baseMapper.selectDeptByAncestor(userQuery.getDeptId());
            List<Long> ids = new LinkedList<>();
            for(DeptPO deptPO:deptPOS){
                ids.add(deptPO.getId());
            }
            ids.add(Long.valueOf(userQuery.getDeptId()));
            wrpper = wrpper.in(UserPO::getDeptId,ids);
        }
        if((BeanUtil.isNotEmpty(userQuery.getUserName()))){
            wrpper = wrpper.eq(UserPO::getUserName,userQuery.getUserName());
        }
        if(BeanUtil.isNotEmpty(userQuery.getPhone())){
            wrpper = wrpper.like(UserPO::getPhone,userQuery.getPhone());
        }
        if(BeanUtil.isNotEmpty(userQuery.getStatus())){
            wrpper = wrpper.eq(UserPO::getStatus,userQuery.getStatus());
        }
        if(BeanUtil.isNotEmpty(userQuery.getCreateTimeStart())){
            wrpper = wrpper.ge(UserPO::getCreateTime,userQuery.getCreateTimeStart());
        }
        if(BeanUtil.isNotEmpty(userQuery.getCreateTimeEnd())){
            wrpper = wrpper.le(UserPO::getCreateTime,userQuery.getCreateTimeEnd());
        }
        IPage<UserPO> userPOPage = this.baseMapper.selectPage(new Page<>(userQuery.getPageIndex(),
                userQuery.getPageSize()), wrpper);
        userPOPage.getRecords().forEach(System.out::println);

        userPOS = userPOPage.getRecords();
        for(UserPO userPO:userPOS){
            UserVO userVO = BeanUtil.copyProperties(userPO,UserVO.class);
            System.out.println(userVO);
            UserVO userVOS1 = this.baseMapper.selectDeptPro(Long.valueOf(userVO.getDeptId()));
            System.out.println("deptId"+userVO.getDeptId());
            System.out.println("uservos1"+userVOS1);
            Map<String,String> map = new LinkedHashMap<>();
            map.put("deptId",Integer.toString(userVO.getDeptId()));
            map.put("deptName",userVOS1.getDeptName());
            map.put("leader",userVOS1.getLeader());
            userVO.setDept(map);
            userVOS.add(userVO);

        }

        PageResult<UserVO> pageResult = new PageResult<>();
        pageResult.setList(userVOS);
        pageResult.setTotal(userPOPage.getTotal());
        return pageResult;
    }

    @Override
    public List deptTree() {
        List<DeptTreeNode> treeList = new LinkedList<>();//整棵树的所有信息
        List<Long> ids = new LinkedList<>();
        //第一个节点的id
        Long firstNodeId = deptMapper.selectOne(new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId,0L)).getId();

        System.out.println("************"+createTreeNode(firstNodeId));
        treeList.add(createTreeNode(firstNodeId));
        return treeList;
    }

    //通过一个节点得到所有子节点的id
    public List<Long> getChildNodeId(Long id){
        List<DeptPO> deptPOList = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>()
                .eq(DeptPO::getSuperiorId, id));
        List<Long> idList = new LinkedList<>();
        for(DeptPO deptPO:deptPOList){
            idList.add(deptPO.getId());
        }
        return idList;
    }

    //通过一个id创建树的节点
    public DeptTreeNode createTreeNode(Long id){
        UserVO userVOS1 = this.baseMapper.selectDeptPro(id);
        System.out.println("userVOS1="+userVOS1);
        DeptTreeNode deptTreeNode = new DeptTreeNode();
        deptTreeNode.setId(id);
        if(userVOS1!=null){
            deptTreeNode.setDeptName(userVOS1.getDeptName());
        }

        DeptTreeNode deptTreeNodeChild = new DeptTreeNode();
        List<DeptTreeNode> nodes = new LinkedList();
        List<Long> ids = new LinkedList<>();
        ids.add(id);
        //该层id组nodeIds
        List<Long> nodeIds = findIdsBySuperId(ids);
        for(Long nodeId:nodeIds){
            nodes.add(createTreeNode(nodeId));
        }
        deptTreeNode.setChildren(nodes);
        return deptTreeNode;
    }


    public List<Long> findIdsBySuperId( List<Long> superiorIds){
        List<Long> ids = new ArrayList<>();
        List<DeptPO> deptPOS = new ArrayList();
        for(Long superiorId:superiorIds) {
            deptPOS = deptMapper.selectList(new LambdaQueryWrapper<DeptPO>().eq(DeptPO::getSuperiorId, superiorId));
            for (DeptPO deptPO : deptPOS) {
                ids.add(deptPO.getId());
            }
        }
        return ids;
    }


}
