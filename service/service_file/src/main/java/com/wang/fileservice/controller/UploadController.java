package com.wang.fileservice.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 王冰炜
 * @create 2021-05-16 16:23
 */

@Api(tags = "文件上传")
@Controller
@RequestMapping("/fileservice/upload")

@CrossOrigin
public class UploadController {

    @GetMapping("/uploadFile")
    @ResponseBody
    public String upload(HttpServletRequest request, HttpServletResponse response){
        return "hello";
    }

}
