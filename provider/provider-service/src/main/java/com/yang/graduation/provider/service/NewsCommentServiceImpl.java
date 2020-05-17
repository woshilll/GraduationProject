package com.yang.graduation.provider.service;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.dto.BackCommentsDto;
import com.yang.graduation.commons.domain.dto.CommentDto;
import com.yang.graduation.commons.domain.dto.FrontCommentsDto;
import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.mapper.NewsCommentMapper;
import com.yang.graduation.commons.mapper.NewsMapper;
import com.yang.graduation.commons.mapper.UserMapper;
import com.yang.graduation.provider.api.NewsCommentService;
import com.yang.graduation.provider.api.UserService;
import com.yang.graduation.provider.util.IdWorker;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/8 20:16
 */
@Service(version = "1.0.0")
public class NewsCommentServiceImpl implements NewsCommentService {

    @Resource
    private NewsCommentMapper newsCommentMapper;
    @Reference(version = "1.0.0")
    private UserService userService;
    @Resource
    private IdWorker idWorker;
    @Resource
    private NewsMapper newsMapper;

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

    @Override
    public List<FrontCommentsDto> frontComments(String newsId) {
        return newsCommentMapper.getFrontCommentsByNewsId(newsId);
    }

    /**
     * 创建评论
     * @param commentDto
     * @return
     */
    @Override
    public int createComment(CommentDto commentDto) {
        User user = userService.getUser(commentDto.getName());
        NewsComment newsComment = new NewsComment();
        newsComment.setCommentDate(new Date());
        newsComment.setId(idWorker.nextId() + "");
        newsComment.setCommentUserId(user.getId());
        newsComment.setDetails(commentDto.getUserDetails());
        newsComment.setNewsId(commentDto.getNewsId());
        newsComment.setStatus(0);
        return newsCommentMapper.insert(newsComment);
    }

    /**
     * 通过newsId找到评论数
     * @param newsId
     * @return
     */
    @Override
    public int getCommentsCountByNewsId(String newsId) {
        Example example = new Example(NewsComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("newsId", newsId)
                .andEqualTo("status", 0);
        return newsCommentMapper.selectCountByExample(example);
    }

    @Override
    public PageInfo<BackCommentsDto> getCommentList(Map<String, Object> map) {
        PageInfo<BackCommentsDto> pageInfo = new PageInfo<>();
        pageInfo.setDraw(0);
        map.put("page", ((int)map.get("page") - 1) * 10);
        pageInfo.setStart((Integer) map.get("page"));
        pageInfo.setLength((Integer) map.get("limit"));
        List<BackCommentsDto> backCommentsDtos = newsCommentMapper.page(map);
        pageInfo.setData(backCommentsDtos);
        int count = count(map);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        return pageInfo;
    }

    @Override
    public int count(Map<String, Object> map) {
        return newsCommentMapper.count(map);
    }
}
