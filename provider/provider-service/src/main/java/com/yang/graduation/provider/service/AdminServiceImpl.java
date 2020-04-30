package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.AdminLogs;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.mapper.AdminLogsMapper;
import com.yang.graduation.commons.mapper.AdminMapper;
import com.yang.graduation.provider.api.AdminService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理员service
 * @author woshilll
 * @date 2020/3/28 23:19
 * @version v1.0.0
 */
@Service(version = "1.0.0")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private IdWorker idWorker;
    /**
     * 注册管理员
     * @param admin admin
     * @return
     */
    @Override
    public int regAdmin(Admin admin) {
        initAdmin(admin);
        return adminMapper.insert(admin);
    }

    /**
     * 通过名称查找admin
     * @param name admin名
     * @return {@link Admin}
     */
    @Override
    public Admin getAdmin(String name) {
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("name", name);
        return adminMapper.selectOneByExample(example);
    }

    /**
     * find admins
     * @param admin {@link Admin}
     * @return {@link Admin}
     */
    @Override
    public List<Admin> getAdmins(Admin admin) {
        return adminMapper.select(admin);
    }

    /**
     * 删除admin
     * @param id admin id
     * @return 1 true o false
     */
    @Override
    public int deleteById(String id) {
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("id", id);
        return adminMapper.deleteByExample(example);
    }

    /**
     * 更新admin
     * @param admin {@link Admin}
     * @return 1 success 0 fail
     */
    @Override
    public int updateById(Admin admin) {
        Admin newAdmin = getAdmin(admin.getName());
        newAdmin.setEmail(admin.getEmail());
        newAdmin.setNickName(admin.getNickName());
        newAdmin.setNode(admin.getNode());
        newAdmin.setStatus(admin.getStatus());
        return adminMapper.updateByPrimaryKeySelective(newAdmin);
    }

    /**
     * 更新用户头像
     * @param path 新的头像地址
     * @param username 用户名
     * @return 1 success 0 fail
     */
    @Override
    public int modifyIcon(String path, String username) {
        Admin admin = getAdmin(username);
        admin.setAdminIcon(path);
        return adminMapper.updateByPrimaryKey(admin);
    }

    /**
     *查询全部
     * @param map 模糊查询 + 分页
     * @return {@link PageInfo}
     */
    @Override
    public PageInfo<Admin> getAdminList(Map<String, Object> map) {
        PageInfo<Admin> pageInfo = new PageInfo<>();
        pageInfo.setDraw(0);
        map.put("page", ((int)map.get("page") - 1) * 10);
        pageInfo.setStart((Integer) map.get("page"));
        pageInfo.setLength((Integer) map.get("limit"));
        List<Admin> admins = adminMapper.page(map);
        pageInfo.setData(admins);
        int count = count(map);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        return pageInfo;
    }

    /**
     * 模糊查询的总数
     * @param map name,email
     * @return {@link Integer}
     */
    @Override
    public int count(Map<String, Object> map) {
        return adminMapper.count(map);
    }

    /**
     * 更新登录时间
     * @param username id
     * @return 1 0
     */
    @Override
    public int updateLoginTime(String username) {
        Admin admin = getAdmin(username);
        admin.setLastLoginTime(new Date());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 更新密码
     * @param name name
     * @param newPwd new password
     * @return 1 0
     */
    @Override
    public int updatePwd(String name, String newPwd) {
        Admin admin = getAdmin(name);
        admin.setPassword(passwordEncoder.encode(newPwd));
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Resource
    private AdminLogsMapper adminLogsMapper;
    /**
     * 记录登录日志
     * @param adminLogs 日志信息
     * @return 1 0
     */
    @Override
    public int loginLogs(AdminLogs adminLogs) {
        Admin admin = getAdmin(adminLogs.getName());
        adminLogs.setLoginTime(new Date());
        adminLogs.setAdminId(admin.getId());
        return adminLogsMapper.insert(adminLogs);
    }

    /**
     * 得到所有的登录日志
     * @return {@link List<AdminLogs>}
     */
    @Override
    public List<AdminLogs> getAdminLogs() {
        return adminLogsMapper.getAll();
    }

    /**
     * 对admin进行初始化操作
     * @param admin
     */
    private void initAdmin(Admin admin) {
        //设置雪花分布式Id
        admin.setId(idWorker.nextId() + "");
        //设置创建时间
        admin.setRegistTime(new Date());
        //设置最后登录时间
        admin.setLastLoginTime(new Date());
        admin.setStatus(0);
        //密码加密处理
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    }
}
