package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 页面跳转 新闻详情以及搜索
 * 不需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/12 15:35
 */
@Controller
public class NewsPostController {
    @Reference(version = "1.0.0")
    private NewsService newsService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/news/post/{id}")
    public String newsPost(@PathVariable String id, Model model, HttpServletRequest request) {
        redisTemplate.boundSetOps("news_read_count" + id).add(request.getRemoteAddr());
        NewsParam newsParam = newsService.getNewsById(id);
        newsParam.setReadCount(redisTemplate.boundSetOps("news_read_count" + id).size());
        model.addAttribute("news", newsParam);
        return "post-full";
    }
    @GetMapping("/news/search/front/{title}")
    public String search(@PathVariable String title, Model model) {
        model.addAttribute("title", title);
        return "search";
    }
}
