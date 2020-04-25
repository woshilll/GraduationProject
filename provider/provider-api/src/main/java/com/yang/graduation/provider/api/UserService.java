package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.dto.UserHoverDto;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 21:35
 */
public interface UserService {
    /**
     * 注册user
     * @param user {@link User}
     * @return 1 success 0 fail
     */
    int regUser(User user);

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return {@link User}
     */
    User getUser(String username);

    /**
     * 删除
     * @param id user id
     * @return 1 success 0 fail
     */
    int deleteById(String id);

    /**
     * 通过id更新user
     * @param user {@link User}
     * @return 1 success 0 fail
     */
    int updateById(User user);

    /**
     * 修改用户头像
     * @param path 头像上传地址
     * @param username 用户名
     * @return 1 success 0 fail
     */
    int modifyIcon(String path, String username);

    /**
     * 获得查询的总量
     * @param map 查询条件字段 name email nickName
     * @return 查询总数
     */
    int count(Map<String, Object> map);

    /**
     * 得到所用的用户
     * @param map 模糊查询 + 分页
     * 一定包含的字段{page(当前页起始位置) limit(每页数据数) name(名称) email(邮箱) nickName(昵称)}
     * @return {@link PageInfo}
     */
    PageInfo<User> getUserList(Map<String, Object> map);

    /**
     * 通过手机号,用户名或者邮箱验证用户是否存在
     * @param key 可以是手机号, 用户名, 邮箱
     * @return {@link User}
     */
    User validateUser(String key);

    /**
     * 更新登录时间
     * @param username 可以是手机号, 用户名, 邮箱
     */
    void updateLoginTime(String username);

    /**
     * 通过id查找
     * @param id
     * @return
     */
    User getById(String id);
    /**
     * 通过用户名获取
     * @param name
     * @return {@link UserHoverDto}
     */
    UserHoverDto getUserHoverDto(String name);

    /**
     * 验证手机号是否存在
     * @param phone
     * @return
     */
    boolean validatePhone(String phone);
    /**
     * 验证邮箱是否存在
     * @param email
     * @return
     */
    boolean validateEmail(String email);

    /**
     * 通过手机得到用户
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);
    /**
     * 通过邮箱得到用户
     * @param email
     * @return
     */
    User getUserByEmail(String email);
}
