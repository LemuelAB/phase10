package com.lagou.eduuserboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAction {
    @GetMapping("hello1")
    public String hello1(){
        System.out.println("你好，老孙！");
        return "Hi,老孙!";
    }
}
