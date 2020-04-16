package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.NewsLike;
import com.yang.graduation.commons.mapper.NewsLikeMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.provider.api.NewsLikeService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

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
}
