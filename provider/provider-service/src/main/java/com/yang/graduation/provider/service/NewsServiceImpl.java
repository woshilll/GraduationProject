package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.mapper.NewsCategoryMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

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

    /**
     * 新增新闻
     * @param newsParam {@link NewsParam}
     * @return 1 0
     */
    @Override
    public int insertNews(NewsParam newsParam) {
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
        return newsParam;
    }

    private void initNews(News news) {
        news.setId(idWorker.nextId() + "");
        news.setPostTime(new Date());
        news.setIsDelete(0);
        news.setStatus(0);
    }
}
