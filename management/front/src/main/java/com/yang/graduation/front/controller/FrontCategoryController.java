package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.provider.api.NewsCategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 分类页
 * 不需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/12 22:04
 */
@Controller
public class FrontCategoryController {
    @Reference(version = "1.0.0")
    private NewsCategoryService newsCategoryService;

    @GetMapping("/category/{id}")
    public String category(@PathVariable Integer id, Model model) {
        NewsCategory newsCategory = newsCategoryService.getOne(id);
        model.addAttribute("categoryId", id);
        model.addAttribute("categoryName", newsCategory.getName());
        return "category";
    }
}
