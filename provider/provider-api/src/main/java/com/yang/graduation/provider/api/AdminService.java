package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.Admin;

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
     * 查找admin
     * @param admin {@link Admin}
     * @return {@link Admin}
     */
    Admin getAdmin(Admin admin);


}
