package com.yang.graduation.crawler.controller;

import com.yang.graduation.crawler.pojo.CrawlerNews;
import com.yang.graduation.crawler.task.Task;
import com.yang.graduation.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/6/7 16:10
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource
    private Task task;

    @PostMapping("/news")
    public ResponseResult<Void> get(@RequestBody CrawlerNews crawlerNews) {
        if (crawlerNews == null || crawlerNews.getCategoryId() < 0 || crawlerNews.getCategoryId() > 10 || crawlerNews.getNewsUrls().length() <= 10) {
            return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "错误!");
        }
        String message = task.getItem(crawlerNews);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, message);
    }
}
