package com.wang.fileservice.controller;


import com.wang.fileservice.entity.TblFile;
import com.wang.fileservice.service.TblFileService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/fileservice/file")
public class TblFileController {
    @Autowired
    TblFileService fileService;

    @GetMapping("findAllFile")
    public List<TblFile> list(){
        return fileService.list(null);
    }

}

