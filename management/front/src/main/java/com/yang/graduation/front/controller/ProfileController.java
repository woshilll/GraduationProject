package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCategoryService;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 个人中心 需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/16 19:56
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Reference(version = "1.0.0")
    private UserService userService;
    @Reference(version = "1.0.0")
    private NewsService newsService;
    @Reference(version = "1.0.0")
    private NewsCategoryService newsCategoryService;

    /**
     * 用户信息
     * @param name
     * @return
     */
    @GetMapping("/user/info/{name}")
    public ResponseResult<User> userInfo(@PathVariable String name) {
        User user = userService.getUser(name);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", user);
    }

    /**
     * 最近发表的三个新闻
     * @param id
     * @return
     */
    @GetMapping("/user/getLikeMost3/{id}")
    public ResponseResult<List<News>> getLikeMost3(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getLikeMost3ByUserId(id));
    }

    /**
     * 评论最多
     * @param id
     * @return
     */
    @GetMapping("/user/getCommentMost/{id}")
    public ResponseResult<News> getCommentMost(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getCommentMostByUserId(id));
    }

    /**
     * 点赞最多
     * @param id
     * @return
     */
    @GetMapping("/user/getLikeMost/{id}")
    public ResponseResult<News> getLikeMost(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getLikeMostByUserId(id));
    }

    /**
     * 所有审核通过
     * @param id
     * @return
     */
    @GetMapping("/user/getAll/{id}")
    public ResponseResult<List<News>> getAll(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.selectAllByUserId(id));
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody User user) {
        int res = userService.updateById(user);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "success");
    }

    /**
     * 所有分类
     * @return
     */
    @GetMapping("/categoryList")
    public ResponseResult<List<NewsCategory>> categoryList() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsCategoryService.getAll());
    }

    /**
     * 创建文章
     * @param newsParam
     * @return
     */
    @PostMapping("/createNews")
    public ResponseResult<Void> createNews(@RequestBody NewsParam newsParam) {
        int res = newsService.insertNews(newsParam);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.INSERT_FAIL, "success");
    }

    /**
     * 所有未审核新闻
     * @param id
     * @return
     */
    @GetMapping("/unReviewed/{id}")
    public ResponseResult<List<News>> unReviewed(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.unReviewed(id));
    }

    /**
     * 所有审核不通过新闻
     * @param id
     * @return
     */
    @GetMapping("/reviewedFail/{id}")
    public ResponseResult<List<News>> reviewedFail(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.reviewedFail(id));
    }

    /**
     * 更新新闻,如果是审核失败的新闻设置状态为0进行重新审核
     * @param news
     * @return
     */
    @PostMapping("/updateNews")
    public ResponseResult<Void> updateNews(@RequestBody News news) {
        news.setStatus(0);
        NewsParam newsParam = new NewsParam();
        BeanUtils.copyProperties(news, newsParam);
        int res = newsService.updateNews(newsParam);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "success");
    }
}
