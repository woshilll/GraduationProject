package com.yang.graduation.commons.tk.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper
 * @author 李洋
 * @date 2020/3/28 17:36
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
