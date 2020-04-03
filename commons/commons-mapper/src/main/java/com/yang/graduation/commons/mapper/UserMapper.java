package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 用户mapper
 * @author yangge666
 */

public interface UserMapper extends MyMapper<User> {

    /**
     * 分页 + 模糊查询
     * @param params 条件
     * 必须具备 page/起始位置 limit/每页长度 name/名称 email/邮箱 nickName/昵称
     * @return {@link List}
     */
    List<User> page(Map<String, Object> params);

    /**
     * 查询总数量
     * @param params name/名称 email/邮箱 nickName/昵称
     * @return 总数
     */
    int count(Map<String, Object> params);
}