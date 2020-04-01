package com.yang.graduation.provider.api;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/3/29 23:24
 */
public interface GraduationService<T> {
    /**
     * 通用插入
     * @param t {@link T}
     * @return 1 success 0 false
     */
    int insert(T t);

    /**
     * 通用删除
     * @param id 主键id
     * @return 1 success 0 false
     */
    int delete(String id);

    /**
     * 通用更新
     * @param t {@link T}
     * @return 1 success 0 false
     */
    int update(T t);

    /**
     * 通用查找
     * @param name 名字
     * @return {@link T}
     */
    T get(String name);

    /**
     * 通用查找集合
     * @param t {@link T}
     * @return {@link T}
     */
    List<T> gets(T t);
}
