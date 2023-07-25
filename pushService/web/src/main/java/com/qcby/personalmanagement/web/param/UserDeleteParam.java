package com.qcby.personalmanagement.web.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: pushService
 * @Author: Yuan Haozhe
 * @CreateTime: 2023-07-21  14:26
 * @Description: TODO
 * @Version: 1.0
 */

@Data
public class UserDeleteParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 需要删除的用户id
     *
     **/
    private List<Long> ids;
}
