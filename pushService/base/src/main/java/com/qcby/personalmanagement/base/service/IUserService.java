package com.qcby.personalmanagement.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qcby.framework.common.pojo.PageResult;
import com.qcby.personalmanagement.base.dto.UserPostRoleDTO;
import com.qcby.personalmanagement.base.dto.UserQuery;
import com.qcby.personalmanagement.base.po.UserPO;
import com.qcby.personalmanagement.base.vo.UserPostRoleVO;
import com.qcby.personalmanagement.base.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IUserService extends IService<UserPO> {
    public Integer add(UserPostRoleDTO userPostRoleDTO);

    public int insertPostRole(UserPostRoleDTO userPostRoleDTO, Long userId);

    public Integer delete(List<Long> ids);

    public Integer update(UserPostRoleDTO userPostRoleDTO);

    public Boolean statusChange(UserQuery userQuery);


    public UserPostRoleVO choose(UserQuery userQuery);

    public Boolean importUser(MultipartFile multipartFile);

    public void exportUser(HttpServletResponse response);
    public void downloadTemplate(HttpServletResponse response);

    public PageResult<UserVO> search(UserQuery userQuery);

    public List deptTree();
}
