package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/user/info/{name}")
    public ResponseResult<User> userInfo(@PathVariable String name) {
        User user = userService.getUser(name);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", user);
    }
    @GetMapping("/user/getLikeMost3/{id}")
    public ResponseResult<List<News>> getLikeMost3(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getLikeMost3ByUserId(id));
    }
    @GetMapping("/user/getCommentMost/{id}")
    public ResponseResult<News> getCommentMost(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getCommentMostByUserId(id));
    }
    @GetMapping("/user/getLikeMost/{id}")
    public ResponseResult<News> getLikeMost(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.getLikeMostByUserId(id));
    }
    @GetMapping("/user/getAll/{id}")
    public ResponseResult<List<News>> getAll(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.selectAllByUserId(id));
    }

}
