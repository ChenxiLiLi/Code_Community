package com.chenxi.community.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Mr.Chen
 * @Description:
 * @Date:Created in 14:20 2020/3/1
 */
@Controller
public class HelloController {
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World")
                                   String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }
}
