package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.CountParam;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.commons.domain.analyze.CommentAndLikeParam;

import java.util.List;

/**
 * 分析
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/6 21:06
 */
public interface AnalyzeService {
    /**
     * 分析每个类型的数量
     * @return {@link CountParam}
     */
    CountParam analyzeCount();

    /**
     * 分析每个分类下新闻的数量
     * @return {@link List<CategoryNewsCountParam>}
     */
    List<CategoryNewsCountParam> analyzeCategoryNewsCountParam();

    /**
     * 分析每个分类下新闻的数量 未删除和状态为1
     * @return {@link List<CategoryNewsCountParam>}
     */
    List<CategoryNewsCountParam> analyzeCategoryNewsCountParam2();

    /**
     * 分析每个分类下点赞和评论数
     * @return {@link List<CommentAndLikeParam>}
     */
    List<CommentAndLikeParam> analyzeCommentCountAndLikeCountInCategory();
}
