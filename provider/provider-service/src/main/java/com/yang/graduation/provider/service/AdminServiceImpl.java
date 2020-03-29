package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.mapper.AdminMapper;
import com.yang.graduation.provider.api.AdminService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

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
     * find admin
     * @param admin {@link Admin}
     * @return {@link Admin}
     */
    @Override
    public Admin getAdmin(Admin admin) {
        return adminMapper.selectOne(admin);
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
        //密码加密处理
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
    }
}
