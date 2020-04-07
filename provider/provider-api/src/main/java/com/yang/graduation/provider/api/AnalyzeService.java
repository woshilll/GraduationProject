package com.yang.graduation.provider.api;

import com.yang.graduation.commons.domain.CountParam;
import com.yang.graduation.commons.domain.analyze.CategoryNewsCountParam;

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
}
