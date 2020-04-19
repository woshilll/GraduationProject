package com.yang.graduation.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/17 22:42
 */
@Controller
public class TemplateController {

    @GetMapping("/my-tinymce")
    public String myTinymce() {
        return "my-tinymce";
    }
}
