package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.NewsLike;
import com.yang.graduation.commons.mapper.NewsLikeMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.provider.api.NewsLikeService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/15 16:23
 */
@Service(version = "1.0.0")
public class NewsLikeServiceImpl implements NewsLikeService {
    @Resource
    private NewsLikeMapper newsLikeMapper;
    @Override
    public int getLikeCountsByNewsId(String newsId) {
        Example example = new Example(NewsLike.class);
        example.createCriteria().andEqualTo("newsId", newsId);
        return newsLikeMapper.selectCountByExample(example);
    }

    @Override
    public NewsLike isLike(String id, String newsId) {
        Example example = new Example(NewsLike.class);
        example.createCriteria().andEqualTo("userId", id)
                .andEqualTo("newsId", newsId);
        return newsLikeMapper.selectOneByExample(example);
    }

    @Override
    public int remove(String id, String newsId) {
        Example example = new Example(NewsLike.class);
        example.createCriteria().andEqualTo("userId", id)
                .andEqualTo("newsId", newsId);
        return newsLikeMapper.deleteByExample(example);
    }

    @Override
    public int create(String id, String newsId) {
        NewsLike newsLike = new NewsLike();
        newsLike.setUserId(id);
        newsLike.setNewsId(newsId);
        newsLike.setLikeTime(new Date());
        return newsLikeMapper.insert(newsLike);
    }
}
