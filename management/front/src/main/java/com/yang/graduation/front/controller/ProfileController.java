package com.yang.graduation.front.controller;

import com.yang.graduation.commons.domain.News;
import com.yang.graduation.commons.domain.NewsCategory;
import com.yang.graduation.commons.domain.NewsParam;
import com.yang.graduation.commons.domain.User;
import com.yang.graduation.dto.ResponseResult;
import com.yang.graduation.provider.api.NewsCategoryService;
import com.yang.graduation.provider.api.NewsService;
import com.yang.graduation.provider.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

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

    /**
     * 验证密码
     * @param id
     * @param oldPwd
     * @return
     */
    @PostMapping("/validatePwd/{id}/{oldPwd}")
    public ResponseResult<Void> validatePwd(@PathVariable String id, @PathVariable String oldPwd) {
        User user = userService.getById(id);
        if (passwordEncoder.matches(oldPwd, user.getPassword())) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.VALIDATE_PWD, "fail");
    }

    /**
     * 更新密码
     * @param id
     * @param password
     * @return
     */
    @PostMapping("/changePwd/{id}/{password}")
    public ResponseResult<Void> changePwd(@PathVariable String id, @PathVariable String password) {
        User user = userService.getById(id);
        user.setPassword(passwordEncoder.encode(password));
        int res = userService.updateById(user);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "fail");
    }

    /**
     * 验证手机号
     * @param phone
     * @return
     */
    @PostMapping("/validatePhone/{phone}")
    public ResponseResult<Void> validatePhone(@PathVariable String phone) {
        if (phone.matches("0?(13|14|15|18|17)[0-9]{9}")) {
            if (!userService.validatePhone(phone)) {
                //手机号不存在
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
            }
        }
        //手机号已存在
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "fail");
    }
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 更新手机号
     * @param id
     * @param phone
     * @param code
     * @return
     */
    @PostMapping("/update/phone/{id}/{phone}/{code}")
    public ResponseResult<Void> updatePhone(@PathVariable String id, @PathVariable String phone, @PathVariable String code) {
        String phoneCode = redisTemplate.boundValueOps(phone).get();
        if (StringUtils.isNotBlank(code) && code.equals(phoneCode)) {
            User user = userService.getById(id);
            if (user.getPhone() != null && user.getPhone().matches("0?(13|14|15|18|17)[0-9]{9}")) {
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户手机号已存在!请更改手机号");
            }
            user.setPhone(phone);
            int res = userService.updateById(user);
            if (res > 0) {
                redisTemplate.delete(phone);
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "update success");
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "update fail");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "验证码有误!");
    }
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    @PostMapping("/validateEmail/{email}")
    public ResponseResult<Void> validateEmail(@PathVariable String email) {
        if (email.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}")) {
            if (!userService.validateEmail(email)) {
                //邮箱不存在
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "success");
            }
        }
        //邮箱已存在
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "fail");
    }
    /**
     * 更新邮箱
     * @param id
     * @param email
     * @param code
     * @return
     */
    @PostMapping("/update/email/{id}/{email}/{code}")
    public ResponseResult<Void> updateEmail(@PathVariable String id, @PathVariable String email, @PathVariable String code) {
        String emailCode = redisTemplate.boundValueOps(email).get();
        if (StringUtils.isNotBlank(code) && code.equals(emailCode)) {
            User user = userService.getById(id);
            if (user.getEmail() != null && user.getEmail().matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}")) {
                return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "用户邮箱已存在");
            }
            user.setEmail(email);
            int res = userService.updateById(user);
            if (res > 0) {
                redisTemplate.delete(email);
                return new ResponseResult<>(ResponseResult.CodeStatus.OK, "update success");
            }
            return new ResponseResult<>(ResponseResult.CodeStatus.UPDATE_FAIL, "update fail");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.FAIL, "验证码有误!");
    }

    /**
     * 假删除,将isDelete字段置为1
     * @param id
     * @return
     */
    @PostMapping("/delete/news/{id}")
    public ResponseResult<Void> deleteNewsById(@PathVariable String id) {
        int res = newsService.deleteById(id);
        if (res > 0) {
            return new ResponseResult<>(ResponseResult.CodeStatus.OK, "delete success");
        }
        return new ResponseResult<>(ResponseResult.CodeStatus.DELETE_FAIL, "delete fail");
    }
}
