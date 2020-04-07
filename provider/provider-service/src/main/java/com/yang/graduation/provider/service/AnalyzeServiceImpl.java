package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.CountParam;
import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.commons.mapper.AdminMapper;
import com.yang.graduation.commons.mapper.NewsCategoryMapper;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.AnalyzeService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分析
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/6 21:06
 */
@Service(version = "1.0.0")
public class AnalyzeServiceImpl implements AnalyzeService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private NewsCommentMapper newsCommentMapper;
    @Resource
    private NewsCategoryMapper newsCategoryMapper;

    /**
     * 分析每个类型的数量
     * @return {@link CountParam}
     */
    @Override
    public CountParam analyzeCount() {
        CountParam countParam = new CountParam();
        int adminCount = adminMapper.selectCount(new Admin());
        int userCount = userMapper.selectCount(new User());
        int newsCount = newsMapper.selectCount(new News());
        int commentCount = newsCommentMapper.selectCount(new NewsComment());
        countParam.setAdminCount(adminCount);
        countParam.setUserCount(userCount);
        countParam.setNewsCount(newsCount);
        countParam.setCommentCount(commentCount);
        return countParam;
    }

    /**
     * 分析每个分类下新闻的数量
     * @return {@link List<CategoryNewsCountParam>}
     */
    @Override
    public List<CategoryNewsCountParam> analyzeCategoryNewsCountParam() {
        return newsCategoryMapper.categoryNewsCount();
    }
}
