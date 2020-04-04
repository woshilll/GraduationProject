package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/4 23:59
 */
@RestController
@RequestMapping("/back/category")
public class NewsCategoryController {
    @Reference(version = "1.0.0")
    private NewsCategoryService newsCategoryService;

    /**
     * 得到所有的新闻分类
     * @return {@link ResponseResult}
     */
    @GetMapping("/get/all")
    public ResponseResult<List<NewsCategory>> getAll() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "获取成功!", newsCategoryService.getAll());
    }
}
