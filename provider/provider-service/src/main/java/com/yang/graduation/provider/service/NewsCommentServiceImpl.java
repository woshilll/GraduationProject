package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.provider.api.NewsCommentService;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/8 20:16
 */
@Service(version = "1.0.0")
public class NewsCommentServiceImpl implements NewsCommentService {

    @Resource
    private NewsCommentMapper newsCommentMapper;

    /**
     * 通过新闻id查到所有的评论
     * @param newsId 新闻ID
     * @return {@link List<NewsComment>}
     */
    @Override
    public List<NewsComment> getCommentsByNewsId(String newsId) {
        Example example = new Example(NewsComment.class);
        example.createCriteria().andEqualTo("newsId", newsId);
        return newsCommentMapper.selectByExample(example);
    }

    /**
     * 根据评论id更新评论状态
     * @param newsComment {@link NewsComment}
     * @return 1 0
     */
    @Override
    public int updateComment(NewsComment newsComment) {
        return newsCommentMapper.updateByPrimaryKeySelective(newsComment);
    }
}
