package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.CountParam;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.commons.domain.analyze.CommentAndLikeParam;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.AnalyzeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分析.
 *
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/6 21:19
 */
@RestController
@RequestMapping("/back/analyze")
public class AnalyzeController {
    @Reference(version = "1.0.0")
    private AnalyzeService analyzeService;

    /**
     * 查询各个模块数量统计
     * @return {@link ResponseResult<CountParam>}
     */
    @GetMapping("/count")
    public ResponseResult<CountParam> analyzeCount() {
        CountParam countParam = analyzeService.analyzeCount();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功!", countParam);
    }

    /**
     * 查询每个分类下新闻的数量
     * @return {@link ResponseResult<List<CategoryNewsCountParam>>}
     */
    @GetMapping("/categoryNewsCount")
    public ResponseResult<List<CategoryNewsCountParam>> analyzeCategoryNewsCount() {
        List<CategoryNewsCountParam> categoryNewsCountParamList = analyzeService.analyzeCategoryNewsCountParam();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功!", categoryNewsCountParamList);
    }

    /**
     * 分析每个分类下点赞和评论数量
     * @return {@link ResponseResult}
     */
    @GetMapping("/commentAndLikeCount")
    public ResponseResult<List<CommentAndLikeParam>> analyzeCommentAndLikeCount() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功!", analyzeService.analyzeCommentCountAndLikeCountInCategory());
    }
}
