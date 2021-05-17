package com.wang.fileservice.controller;

import com.wang.commonutils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author 王冰炜
 * @create 2021-05-16 16:35
 */

@Api(tags = "登录退出")
@RestController
@RequestMapping("/fileservice/user")
@CrossOrigin //解决跨域
public class UserLoginController {
    //login
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Response login(){
        return Response.ok().data("token","admin");
    }

    //info
    @ApiOperation(value = "获取用户信息")
    @GetMapping("info")
    public Response info(){
        return Response.ok()
                .data("roles","admin")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("introduction","This is admin!");
    }

    //logout
    @ApiOperation(value = "退出")
    @PostMapping("logout")
    public Response logout(){
        return Response.ok()
                .data("token","admin")
                .data("roles","admin");
    }

}
