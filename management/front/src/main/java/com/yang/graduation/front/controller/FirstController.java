package com.yang.graduation.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author woshilll
 * @version v1.0.0
 * @date 2020/4/11 12:47
 */
@Controller
public class FirstController {
    @GetMapping("/index")
    public String firstPage() {
        return "index";
    }
}
