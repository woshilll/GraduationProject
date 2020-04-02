package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 李洋
 * @date 2020/3/28 23:13
 */
public interface AdminService {
    /**
     * 注册admin
     * @param admin {@link Admin}
     * @return 1 true , 0 false
     */
    int regAdmin(Admin admin);

    /**
     * 查找admin
     * @param name admin名
     * @return {@link Admin}
     */
    Admin getAdmin(String name);

    /**
     * 查找admins
     * @param admin {@link Admin}
     * @return {@link Admin}
     */
    List<Admin> getAdmins(Admin admin);

    /**
     * 根据id删除admin
     * @param id admin id
     * @return 1 true 0 false
     */
    int deleteById(String id);

    /**
     * 通过id更新admin
     * @param admin {@link Admin}
     * @return 1 success 0 fail
     */
    int updateById(Admin admin);

    /**
     * 修改头像
     * @param path 新的头像地址
     * @param username 用户名
     * @return 1 success 0 fail
     */
    int modifyIcon(String path, String username);

    /**
     * 得到所有的管理用户
     * @param map 模糊查询 + 分页
     * @return {@link PageInfo}
     */
    PageInfo<Admin> getAdminList(Map<String, Object> map);

    /**
     * 获得模糊查询的总量
     * @param map name,email
     * @return {@link Integer}
     */
    int count(Map<String, Object> map);

}
