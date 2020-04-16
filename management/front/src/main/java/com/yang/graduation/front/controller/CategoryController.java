package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCategoryService;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/12 22:22
 */
@RestController
public class CategoryController {
    @Reference(version = "1.0.0")
    private NewsService newsService;
    @Reference(version = "1.0.0")
    private NewsCategoryService newsCategoryService;

    @GetMapping("/category/getNews/{id}")
    public ResponseResult<List<NewsParam>> getNews(@PathVariable Integer id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getNewsByCategory(id));
    }
    @GetMapping("/category/category/{id}")
    public ResponseResult<NewsCategory> category(@PathVariable Integer id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsCategoryService.getOne(id));
    }
}
