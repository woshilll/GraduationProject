package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.UserLogs;
import com.yang.graduation.commons.domain.dto.UserHoverDto;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.commons.mapper.NewsLikeMapper;
import com.yang.graduation.commons.mapper.UserLogsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.UserService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/2 22:04
 */
@Service(version = "1.0.0")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private IdWorker idWorker;
    @Resource
    private NewsLikeMapper newsLikeMapper;
    @Resource
    private NewsCommentMapper newsCommentMapper;

    /**
     * 注册用户
     * @param user {@link User}
     * @return
     */
    @Override
    public int regUser(User user) {
        initUser(user);
        return userMapper.insert(user);
    }

    /**
     * 查找用户
     * @param username 用户名
     * @return
     */
    @Override
    public User getUser(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("name", username);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 删除
     * @param id user id
     * @return 1 success 0 fail
     */
    @Override
    public int deleteById(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过id更新user
     * @param user {@link User}
     * @return 1 success 0 fail
     */
    @Override
    public int updateById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 修改用户头像
     * @param path 头像上传地址
     * @param username 用户名
     * @return 1 success 0 fail
     */
    @Override
    public int modifyIcon(String path, String username) {
        User user = getUser(username);
        user.setUserIcon(path);
        return userMapper.updateByPrimaryKey(user);
    }

    /**
     * 获得查询的总量
     * @param map 查询条件字段 name email nickName
     * @return 查询总数
     */
    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }

    /**
     * 得到所用的用户
     * @param map 模糊查询 + 分页
     * 一定包含的字段{page(当前页起始位置) limit(每页数据数) name(名称) email(邮箱) nickName(昵称)}
     * @return {@link PageInfo}
     */
    @Override
    public PageInfo<User> getUserList(Map<String, Object> map) {
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setDraw(0);
        map.put("page", ((int)map.get("page") - 1) * 10);
        pageInfo.setStart((Integer) map.get("page"));
        pageInfo.setLength((Integer) map.get("limit"));
        List<User> users = userMapper.page(map);
        pageInfo.setData(users);
        int count = count(map);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        return pageInfo;
    }

    /**
     * 通过手机号,用户名或者邮箱验证用户是否存在
     * @param key 可以是手机号, 用户名, 邮箱
     * @return {@link User}
     */
    @Override
    public User validateUser(String key) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("phone", key)
                .orEqualTo("name", key)
                .orEqualTo("email", key);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 更新登录时间
     * @param username 可以是手机号, 用户名, 邮箱
     */
    @Override
    public void updateLoginTime(String username) {
        User user = validateUser(username);
        user.setLastLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public UserHoverDto getUserHoverDto(String name) {
        User user = getUser(name);
        UserHoverDto userHoverDto = new UserHoverDto();
        userHoverDto.setId(user.getId());
        userHoverDto.setNickName(user.getNickName());
        userHoverDto.setIcon(user.getUserIcon());
        userHoverDto.setStatus(user.getBanned());
        userHoverDto.setGetCommentCount(newsCommentMapper.getCommentCountByNewsAuthorId(user.getId()));
        userHoverDto.setGetLikeCount(newsLikeMapper.getLikeCountByNewsAuthorId(user.getId()));
        return userHoverDto;
    }

    /**
     * 验证手机号是否存在
     * @param phone
     * @return
     */
    @Override
    public boolean validatePhone(String phone) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("phone", phone);
        return userMapper.selectByExample(example).size() > 0;
    }

    /**
     * 验证邮箱是否存在
     * @param email
     * @return
     */
    @Override
    public boolean validateEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        return userMapper.selectByExample(example).size() > 0;
    }

    /**
     * 通过手机得到用户
     * @param phone
     * @return
     */
    @Override
    public User getUserByPhone(String phone) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("phone", phone);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 通过邮箱得到用户
     * @param email
     * @return
     */
    @Override
    public User getUserByEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        return userMapper.selectOneByExample(example);
    }

    @Resource
    private UserLogsMapper userLogsMapper;
    /**
     * 记录登录日志
     * @param userLogs 日志信息
     * @return 1 0
     */
    @Override
    public int loginLogs(UserLogs userLogs) {
        User user = getUser(userLogs.getName());
        userLogs.setUserId(user.getId());
        userLogs.setLoginTime(new Date());
        return userLogsMapper.insert(userLogs);
    }

    private void initUser(User user) {
        //设置雪花分布式id
        user.setId(idWorker.nextId() + "");
        user.setRegistTime(new Date());
        user.setLastLoginTime(new Date());
        user.setNickName(user.getName());
        user.setBanned(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
