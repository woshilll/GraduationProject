package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.AnalyzeService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/11 16:05
 */
@RestController
@RequestMapping("/analyze")
public class AnalyzeController {
    @Reference(version = "1.0.0")
    private AnalyzeService analyzeService;

    /**
     * 查询每个分类下新闻的数量
     * @return {@link ResponseResult<List<CategoryNewsCountParam>>}
     */
    @GetMapping("/categoryNewsCount")
    public ResponseResult<List<CategoryNewsCountParam>> analyzeCategoryNewsCount() {
        List<CategoryNewsCountParam> categoryNewsCountParamList = analyzeService.analyzeCategoryNewsCountParam2();
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功!", categoryNewsCountParamList);
    }
}
