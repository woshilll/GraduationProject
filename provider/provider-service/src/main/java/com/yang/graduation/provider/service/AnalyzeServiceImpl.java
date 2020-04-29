package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.CountParam;
import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.domain.NewsLike;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.commons.domain.analyze.CommentAndLikeParam;
import com.yang.graduation.commons.domain.dto.NewsStatus;
import com.yang.graduation.commons.mapper.AdminMapper;
import com.yang.graduation.commons.mapper.NewsCategoryMapper;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.commons.mapper.NewsLikeMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.AnalyzeService;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /**
     * 分析每个分类下新闻的数量 未删除和状态为1
     * @return {@link List<CategoryNewsCountParam>}
     */
    @Override
    public List<CategoryNewsCountParam> analyzeCategoryNewsCountParam2() {
        return newsCategoryMapper.categoryNewsCount2();
    }

    @Resource
    private NewsLikeMapper newsLikeMapper;
    /**
     * 分析每个分类下评论数和点赞数
     * @return {@link List<CommentAndLikeParam>}
     */
    @Override
    public List<CommentAndLikeParam> analyzeCommentCountAndLikeCountInCategory() {
        List<CommentAndLikeParam> commentAndLikeParams = newsCategoryMapper.categoryCommentAndLikeCount();
        for (CommentAndLikeParam commentAndLikeParam : commentAndLikeParams) {
            Example example = new Example(News.class);
            example.createCriteria().andEqualTo("categoryId", commentAndLikeParam.getCategoryId());
            List<News> news = newsMapper.selectByExample(example);
            int commentCount = 0;
            int likeCount = 0;
            for (News news1 : news) {
                Example commentExample = new Example(NewsComment.class);
                commentExample.createCriteria().andEqualTo("newsId", news1.getId());
                commentCount += newsCommentMapper.selectCountByExample(commentExample);
                Example likeExample = new Example(NewsLike.class);
                likeExample.createCriteria().andEqualTo("newsId", news1.getId());
                likeCount += newsLikeMapper.selectCountByExample(likeExample);
            }
            commentAndLikeParam.setCommentCount(commentCount);
            commentAndLikeParam.setLikeCount(likeCount);
        }
        return commentAndLikeParams;
    }

    @Override
    public List<NewsStatus> analyzeNewsStatus() {
        List<NewsStatus> list = new ArrayList<>();
        Example ex = new Example(News.class);
        ex.createCriteria().andEqualTo("status", 0);
        list.add(new NewsStatus(0, newsMapper.selectCountByExample(ex)));
        ex.clear();
        ex.createCriteria().andEqualTo("status", 1);
        list.add(new NewsStatus(1, newsMapper.selectCountByExample(ex)));
        ex.clear();
        ex.createCriteria().andEqualTo("status", 2);
        list.add(new NewsStatus(2, newsMapper.selectCountByExample(ex)));
        return list;
    }
}
