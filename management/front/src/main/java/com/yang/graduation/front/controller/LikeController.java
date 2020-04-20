package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.NewsLike;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsLikeService;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/20 13:36
 */
@RestController
@RequestMapping("/like")
public class LikeController {
    @Reference(version = "1.0.0")
    private NewsLikeService newsLikeService;
    @Reference(version = "1.0.0")
    private UserService userService;

    @GetMapping("/isLike/{username}/{newsId}")
    public ResponseResult<Void> isLike(@PathVariable String username, @PathVariable String newsId) {
        User user = userService.getUser(username);
        NewsLike newsLike = newsLikeService.isLike(user.getId(), newsId);
        if (newsLike != null) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "fail");
    }

    @PostMapping("/remove/{username}/{newsId}")
    public ResponseResult<Void> remove(@PathVariable String username, @PathVariable String newsId) {
        User user = userService.getUser(username);
        newsLikeService.remove(user.getId(), newsId);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
    }

    @PostMapping("/create/{username}/{newsId}")
    public ResponseResult<Void> create(@PathVariable String username, @PathVariable String newsId) {
        User user = userService.getUser(username);
        newsLikeService.create(user.getId(), newsId);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
    }
}
