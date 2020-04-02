package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

/**
 * 管理员mapper
 * @author yangge666
 */
public interface AdminMapper extends MyMapper<Admin> {
    /**
     * 分页 + 模糊查询
     * @param params 条件 必须具备 start/起始位置 length/每页长度
     * @return {@link List}
     */
    List<Admin> page(Map<String, Object> params);

    /**
     * 查询总数量
     * @param params
     * @return
     */
    int count(Map<String, Object> params);
}