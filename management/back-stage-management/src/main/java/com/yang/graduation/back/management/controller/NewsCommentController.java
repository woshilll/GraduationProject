package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.Admin;
import com.yang.graduation.commons.domain.NewsComment;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.commons.domain.dto.BackCommentsDto;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCommentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/8 21:15
 */
@RestController
@RequestMapping("/back/newsComment")
public class NewsCommentController {
    @Reference(version = "1.0.0")
    private NewsCommentService newsCommentService;

    /**
     * 通过新闻id找到所有评论
     * @param newsId 新闻id
     * @return
     */
    @GetMapping("/getCommentsByNewsId")
    public ResponseResult<List<NewsComment>> getCommentsByNewsId(String newsId) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功!", newsCommentService.getCommentsByNewsId(newsId));
    }

    /**
     * 通过id更新评论状态
     * @param newsComment
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> update(@RequestBody NewsComment newsComment) {
        int res = newsCommentService.updateComment(newsComment);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败!");
    }

    /**
     * 模糊查询所有的评论
     * @param map page/limit/details
     * @return
     */
    @PostMapping("/select/all")
    public ResponseResult<PageInfo<BackCommentsDto>> selectAll(@RequestBody Map<String, Object> map) {
        PageInfo<BackCommentsDto> backCommentsDtoPageInfo = newsCommentService.getCommentList(map);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", backCommentsDtoPageInfo);
    }
}
