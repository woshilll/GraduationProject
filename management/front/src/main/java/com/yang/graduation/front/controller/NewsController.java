package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/11 16:32
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Reference(version = "1.0.0")
    private NewsService newsService;
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
}
