package com.wang.servicebase.exceptionhandler;

import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 王冰炜
 * @create 2021-05-15 18:13
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    //全局异常处理
    //指定何种异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response error(Exception e){
        e.printStackTrace();
        return Response.error().message("执行了自定义全局异常处理");
    }

    //自定义异常
    @ExceptionHandler(MatrixException.class)
    @ResponseBody//它不在controller中，为了返回数据
    public Response error(MatrixException e){
        e.printStackTrace();
        return Response.error().message(e.getMsg()).code(e.getCode());
    }
}
