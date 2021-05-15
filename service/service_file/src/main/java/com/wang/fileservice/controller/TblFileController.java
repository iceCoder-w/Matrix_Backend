package com.wang.fileservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.wang.commonutils.Response;
import com.wang.fileservice.entity.TblFile;
import com.wang.fileservice.service.TblFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "测试新建文件")
    @PostMapping("createFile")
    public Response createFile( @ApiParam(name = "File",value = "文件对象",required = true)
                                    @RequestBody TblFile file){
        boolean save = fileService.save(file);
        if (save){
            return Response.ok().message("添加成功");
        }else {
            return Response.error().message("添加失败");
        }
    }


}

