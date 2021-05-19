package com.wang.fileservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.fileservice.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:52
 */

@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {
    IPage<FileInfo> selectFileList(IPage<FileInfo> page, FileInfo fileInfo);
}