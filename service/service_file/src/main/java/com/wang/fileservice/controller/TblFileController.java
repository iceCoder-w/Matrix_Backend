package com.wang.fileservice.controller;


import com.wang.commonutils.Response;
import com.wang.fileservice.entity.TblFile;
import com.wang.fileservice.service.TblFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-15
 */

@Api(tags = "文件管理")
@RestController
@RequestMapping("/fileservice/file")
@CrossOrigin
public class TblFileController {
    @Autowired
    TblFileService fileService;

    @ApiOperation(value = "测试所有文件列表")
    @GetMapping("findAllFile")
    public List<TblFile> list(){
        return fileService.list(null);
    }

    @ApiOperation(value = "测试Response返回体")
    @GetMapping("getFileList")
    public Response getAll(){
        List<TblFile> list = fileService.list(null);
        return Response.ok().data("filelist",list);
    }

}

