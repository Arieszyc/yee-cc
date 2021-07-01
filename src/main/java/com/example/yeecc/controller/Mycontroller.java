package com.example.yeecc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Mycontroller {

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
}
