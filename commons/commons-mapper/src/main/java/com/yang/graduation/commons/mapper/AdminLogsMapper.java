package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.AdminLogs;
import com.yang.graduation.commons.tk.mybatis.MyMapper;

import java.util.List;


/**
 * 管理员日志mapper
 * @author yangge666
 */
public interface AdminLogsMapper extends MyMapper<AdminLogs> {
    /**
     * 时间排序得到所有日志
     * @return {@link List<AdminLogs>}
     */
    List<AdminLogs> getAll();

}