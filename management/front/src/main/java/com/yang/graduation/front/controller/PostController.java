package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.dto.CommentDto;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCommentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 用于新建等操作
 * 需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/15 13:39
 */
@Controller
@RequestMapping("/post")
public class PostController {
    @Reference(version = "1.0.0")
    private NewsCommentService newsCommentService;

    @PostMapping("/createComment")
    @ResponseBody
    private ResponseResult<Void> createComment(@RequestBody CommentDto commentDto) {
        if (newsCommentService.createComment(commentDto) > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.INSERT_FAIL, "fail");
    }
}
