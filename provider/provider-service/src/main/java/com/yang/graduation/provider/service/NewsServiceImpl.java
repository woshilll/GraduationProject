package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.dto.UserHoverDto;
import com.yang.graduation.commons.mapper.NewsCategoryMapper;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.commons.mapper.NewsLikeMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.NewsCommentService;
import com.yang.graduation.provider.api.NewsLikeService;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/3 23:11
 */
@Service(version = "1.0.0")
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    @Resource
    private IdWorker idWorker;

    @Resource
    private NewsCategoryMapper newsCategoryMapper;

    @Resource
    private UserMapper userMapper;
    @Reference(version = "1.0.0")
    private NewsCommentService newsCommentService;
    @Reference(version = "1.0.0")
    private NewsLikeService newsLikeService;


    /**
     * 新增新闻
     * @param newsParam {@link NewsParam}
     * @return 1 0
     */
    @Override
    public int insertNews(NewsParam newsParam) {
        //说明此时为管理员新增新闻,管理员专用用户账号8个8
        if (newsParam.getUserId() == null || newsParam.getUserId().equals("")) {
            newsParam.setUserId("88888888");
            newsParam.setStatus(1);
        } else {
            newsParam.setStatus(0);
        }
        News news = new News();
        BeanUtils.copyProperties(newsParam, news);
        initNews(news);
        return newsMapper.insert(news);
    }

    /**
     * 统计数量
     * @param map 查询字段 title status 时间区间 start end
     * @return count
     */
    @Override
    public int count(Map<String, Object> map) {
        return newsMapper.count(map);
    }

    @Override
    public PageInfo<NewsParam> getNewsList(Map<String, Object> map) {
        PageInfo<NewsParam> paramPageInfo = new PageInfo<>();
        paramPageInfo.setDraw(0);
        map.put("page", ((int)map.get("page") - 1) * 10);
        paramPageInfo.setStart((Integer) map.get("page"));
        paramPageInfo.setLength((Integer) map.get("limit"));
        List<NewsParam> newsParams = newsMapper.page(map);
        paramPageInfo.setData(newsParams);
        int count = count(map);
        paramPageInfo.setRecordsTotal(count);
        paramPageInfo.setRecordsFiltered(count);
        return paramPageInfo;
    }

    /**
     * 通过id查找新闻
     * @param id id
     * @return {@link NewsParam}
     */
    @Override
    public NewsParam getNewsById(String id) {
        News news = newsMapper.selectByPrimaryKey(id);
        NewsParam newsParam = new NewsParam();
        BeanUtils.copyProperties(news, newsParam);
        NewsCategory newsCategory = newsCategoryMapper.selectByPrimaryKey(newsParam.getCategoryId());
        newsParam.setCategoryName(newsCategory.getName());
        User user = userMapper.selectByPrimaryKey(newsParam.getUserId());
        newsParam.setAuthorName(user.getName());
        newsParam.setCommentCount(newsCommentService.getCommentsCountByNewsId(id));
        newsParam.setLikeCount(newsLikeService.getLikeCountsByNewsId(id));
        return newsParam;
    }

    /**
     * 更新新闻
     * @param newsParam {@link NewsParam}
     * @return 1 0
     */
    @Override
    public int updateNews(NewsParam newsParam) {
        News news = new News();
        BeanUtils.copyProperties(newsParam, news);
        return newsMapper.updateByPrimaryKeySelective(news);
    }

    /**
     * 最新的4条新闻
     * @return
     */
    @Override
    public List<News> least4() {
        return newsMapper.least4();
    }

    /**
     * 点赞数最多的4条新闻
     * @return
     */
    @Override
    public List<NewsParam> likeMost4() {
        return initCount(newsMapper.likeMost4());
    }

    /**
     * 评论最多的两个
     * @return
     */
    @Override
    public List<NewsParam> commentMost2() {
        return initCount(newsMapper.commentMost2());
    }

    /**
     * 随机五条新闻
     * @return
     */
    @Override
    public List<NewsParam> random5() {
        return initCount(newsMapper.random5());
    }

    /**
     * admin发布的新闻 4条
     * @return
     */
    @Override
    public List<NewsParam> adminPost4() {
        return initCount(newsMapper.adminPost4());
    }

    /**
     * 通过分类得到新闻
     * @param categoryId
     * @return
     */
    @Override
    public List<NewsParam> getNewsByCategory(Integer categoryId) {
        return initCount(newsMapper.getNewsByCategory(categoryId));
    }

    /**
     * 查找新闻
     * @param title
     * @return
     */
    @Override
    public List<NewsParam> search(String title) {
        return initCount(newsMapper.search(title));
    }

    /**
     * 个人中心展示
     * @param id
     * @return
     */
    @Override
    public List<News> getLikeMost3ByUserId(String id) {
        List<News> newsList = newsMapper.getLikeMost3ByUserId(id);
        for (News news : newsList) {
            news.setCommentCount(newsCommentService.getCommentsCountByNewsId(news.getId()));
            news.setLikeCount(newsLikeService.getLikeCountsByNewsId(news.getId()));
        }
        return newsList;
    }

    /**
     * 根据用户id找到评论最多的新闻
     * @param id
     * @return
     */
    @Override
    public News getCommentMostByUserId(String id) {
        News commentMostByUserId = newsMapper.getCommentMostByUserId(id);
        if (commentMostByUserId == null) {
            commentMostByUserId = newsMapper.selectOneByNew(id);
        }
        return commentMostByUserId;
    }

    /**
     * 根据用户id找到点赞最多的新闻
     * @param id
     * @return
     */
    @Override
    public News getLikeMostByUserId(String id) {
        News likeMostByUserId = newsMapper.getLikeMostByUserId(id);
        if (likeMostByUserId == null) {
            likeMostByUserId = newsMapper.selectOneByNew(id);
        }
        return likeMostByUserId;
    }
    /**
     * 找到当前用户所有新闻
     * @param id
     * @return
     */
    @Override
    public List<News> selectAllByUserId(String id) {
        Example example = new Example(News.class);
        example.createCriteria().andEqualTo("userId", id)
                .andEqualTo("status", 1)
                .andEqualTo("isDelete", 0);
        return newsMapper.selectByExample(example);
    }

    /**
     * 用户id下所有未审核
     * @param id
     * @return
     */
    @Override
    public List<News> unReviewed(String id) {
        Example example = new Example(News.class);
        example.createCriteria().andEqualTo("userId", id)
                .andEqualTo("status", 0)
                .andEqualTo("isDelete", 0);
        return newsMapper.selectByExample(example);
    }

    /**
     * 用户id下所有审核不通过
     * @param id
     * @return
     */
    @Override
    public List<News> reviewedFail(String id) {
        Example example = new Example(News.class);
        example.createCriteria().andEqualTo("userId", id)
                .andEqualTo("status", 2)
                .andEqualTo("isDelete", 0);
        return newsMapper.selectByExample(example);
    }

    private void initNews(News news) {
        news.setId(idWorker.nextId() + "");
        news.setPostTime(new Date());
        news.setIsDelete(0);
        news.setCommentCount(0);
        news.setLikeCount(0);
    }
    private List<NewsParam> initCount(List<NewsParam> newsParams) {
        for (NewsParam newsParam : newsParams) {
            newsParam.setCommentCount(newsCommentService.getCommentsCountByNewsId(newsParam.getId()));
            newsParam.setLikeCount(newsLikeService.getLikeCountsByNewsId(newsParam.getId()));
        }
        return newsParams;
    }
}
