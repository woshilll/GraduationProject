package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.Admin;

import java.util.List;

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


}
