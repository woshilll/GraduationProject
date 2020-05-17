package com.yang.graduation.commons.mapper;


import com.yang.graduation.commons.domain.dto.BackCommentsDto;
import com.yang.graduation.commons.domain.dto.FrontCommentsDto;
import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.tk.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论mapper
 * @author yangge666
 */

public interface NewsCommentMapper extends MyMapper<NewsComment> {
    /**
     * 前台通过新闻id得到评论
     * @param id
     * @return
     */
    List<FrontCommentsDto> getFrontCommentsByNewsId(@Param("id") String id);

    /**
     * 通过新闻作者id得到已发布新闻的真实评论数
     * @param id
     * @return
     */
    int getCommentCountByNewsAuthorId(@Param("id") String id);
    /**
     * 分页 + 模糊查询
     * @param params 条件 必须具备 start/起始位置 length/每页长度
     * @return {@link List}
     */
    List<BackCommentsDto> page(Map<String, Object> params);

    /**
     * 查询总数量
     * @param params
     * @return
     */
    int count(Map<String, Object> params);
}