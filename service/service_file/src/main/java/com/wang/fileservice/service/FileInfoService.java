package com.wang.fileservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.fileservice.entity.FileInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:57
 */

public interface FileInfoService extends IService<FileInfo> {
    /**
     * 合并文件
     * @param fileInfo
     * @return
     */
    HttpServletResponse mergeFile(FileInfo  fileInfo, HttpServletResponse response);

    /**
     * 查询文件列表
     * @param file
     * @return
     */
    IPage<FileInfo> selectFileList(FileInfo file, Integer pageNum, Integer pageSize);

    /**
     * 删除
     * @param fileInfo
     * @return
     */
    Integer deleteFile(FileInfo fileInfo);
}