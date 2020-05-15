package com.yang.graduation.back.management.controller;

import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.PageInfo;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/4 14:35
 */
@RestController
@RequestMapping("/back/news")
public class NewsController {

    @Reference(version = "1.0.0")
    private NewsService newsService;

    /**
     * 模糊查询 + 分页查询
     *
     * @param map 必定包含的字段 page/起始 limit/每页大小 title/ status/ 时间区间/start end
     * @return {@link ResponseResult}
     */
    @PostMapping("/select/all")
    public ResponseResult<PageInfo<NewsParam>> selectAll(@RequestBody Map<String, Object> map) {
        PageInfo<NewsParam> newsParamList = newsService.getNewsList(map);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", newsParamList);
    }

    /**
     * 得到某一新闻的详细信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseResult<NewsParam> getNewsById(@PathVariable String id) {
        NewsParam newsParam = newsService.getNewsById(id);
        return new ResponseResult<>(ResponseResult.CodeStatus.OK, "查询成功", newsParam);
    }

    /**
     * 更新
     * @param newsParam
     * @return
     */
    @PostMapping("/update")
    public ResponseResult<Void> updateNews(@RequestBody NewsParam newsParam) {
        int res = newsService.updateNews(newsParam);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败");
    }

    /**
     * 新增新闻
     * @param newsParam
     * @return
     */
    @PostMapping("/insert")
    public ResponseResult<Void> insert(@RequestBody NewsParam newsParam) {
        int res = newsService.insertNews(newsParam);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "新增新闻成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.INSERT_FAIL, "新增新闻失败!");
    }

    /**
     * 删除新闻 真删除
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public ResponseResult<Void> delete(@PathVariable String id) {
        int res = newsService.deleteTrueById(id);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "删除新闻成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.DELETE_FAIL, "删除新闻失败!");
    }
    /**
     * 不通过
     * @param newsParam
     * @return
     */
    @PostMapping("/noPass")
    public ResponseResult<Void> noPass(@RequestBody NewsParam newsParam) {
        newsParam.setStatus(2);
        int res = newsService.updateNews(newsParam);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "更新成功!");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "更新失败");
    }
    @PostMapping("/batch/audit")
    public ResponseResult<Integer> batchAudit(@RequestBody String[] ids) {
        assert ids.length > 0;
        int res = newsService.batchAudit(ids);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "批量审核成功!", res);
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "批量审核失败");
    }
}
