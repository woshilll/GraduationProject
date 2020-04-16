package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.mapper.NewsCategoryMapper;
import com.yang.graduation.provider.api.NewsCategoryService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/4 23:39
 */
@Service(version = "1.0.0")
public class NewsCategoryServiceImpl implements NewsCategoryService {
    @Resource
    private NewsCategoryMapper newsCategoryMapper;

    /**
     * 查询全部新闻分类
     * @return {@link List}
     */
    @Override
    public List<NewsCategory> getAll() {
        return newsCategoryMapper.selectAll();
    }

    @Override
    public NewsCategory getOne(Integer id) {
        return newsCategoryMapper.selectByPrimaryKey(id);
    }
}
