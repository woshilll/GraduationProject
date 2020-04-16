package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.User;
import com.yang.graduation.commons.domain.dto.AuthorDto;
import com.yang.graduation.commons.domain.dto.FrontCommentsDto;
import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCommentService;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页基本信息
 * 不需要认证
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/11 16:32
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Reference(version = "1.0.0")
    private NewsService newsService;
    @Reference(version = "1.0.0")
    private NewsCommentService newsCommentService;
    @Reference(version = "1.0.0")
    private UserService userService;
    /**
     * 得到最新的四条新闻
     * @return
     */
    @GetMapping("/least4")
    public ResponseResult<List<News>> least4() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功", newsService.least4());
    }

    /**
     * 点赞最多的四个新闻
     * @return
     */
    @GetMapping("/likeMost4")
    public ResponseResult<List<NewsParam>> likeMost4() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功", newsService.likeMost4());
    }
    /**
     * 评论最多的2个新闻
     * @return
     */
    @GetMapping("/commentMost2")
    public ResponseResult<List<NewsParam>> commentMost2() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功", newsService.commentMost2());
    }
    /**
     * 随机5个新闻
     * @return
     */
    @GetMapping("/random5")
    public ResponseResult<List<NewsParam>> random5() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功", newsService.random5());
    }
    /**
     * admin4个新闻
     * @return
     */
    @GetMapping("/adminPost4")
    public ResponseResult<List<NewsParam>> adminPost4() {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "成功", newsService.adminPost4());
    }
    @GetMapping("/comments/{id}")
    public ResponseResult<List<FrontCommentsDto>> comments(@PathVariable String id) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsCommentService.frontComments(id));
    }
    @GetMapping("/author/{id}")
    public ResponseResult<AuthorDto> author(@PathVariable String id) {
        User user = userService.getById(id);
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(user, authorDto);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", authorDto);
    }
    @GetMapping("/search/{title}")
    public ResponseResult<List<NewsParam>> search(@PathVariable String title) {
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success", newsService.search(title));
    }
}
