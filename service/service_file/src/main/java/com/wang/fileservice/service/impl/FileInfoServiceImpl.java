package com.wang.fileservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.fileservice.entity.ChunkInfo;
import com.wang.fileservice.entity.FileInfo;
import com.wang.fileservice.mapper.ChunkInfoMapper;
import com.wang.fileservice.mapper.FileInfoMapper;
import com.wang.fileservice.service.FileInfoService;
import com.wang.fileservice.util.FileInfoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:58
 */

@Service

public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
    @Value("${base.file-path}")
    private String uploadFolder;
    @Autowired
    private ChunkInfoMapper chunkInfoMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    private final static Logger logger = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    @Override
    public HttpServletResponse mergeFile(FileInfo fileInfo, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);

        //进行文件的合并操作
        String filename = fileInfo.getFileName();
        String file = uploadFolder + File.separator + fileInfo.getIdentifier() + File.separator + filename;
        String folder = uploadFolder + File.separator + fileInfo.getIdentifier();
        Integer fileSuccess = FileInfoUtils.merge(file, folder, filename);
        fileInfo.setLocation(folder);
        QueryWrapper<ChunkInfo> wrapper = new QueryWrapper<>();
        wrapper
                .eq(ChunkInfo.COL_IDENTIFIER, fileInfo.getIdentifier())
                .eq(ChunkInfo.COL_FILE_NAME, fileInfo.getFileName());
        chunkInfoMapper.delete(wrapper);
        //文件合并成功后，保存记录
        if (fileSuccess == HttpServletResponse.SC_OK) {
            fileInfoMapper.insert(fileInfo);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        //如果已经存在，则判断是否是同一个项目，同一个项目不用新增记录，否则新增
        else if (fileSuccess == HttpServletResponse.SC_MULTIPLE_CHOICES) {
            QueryWrapper<FileInfo> wrapper1 = new QueryWrapper<>();
            wrapper1
                    .eq(ChunkInfo.COL_IDENTIFIER, fileInfo.getIdentifier())
                    .eq(ChunkInfo.COL_FILE_NAME, fileInfo.getFileName());
            List<FileInfo> tfList = fileInfoMapper.selectList(wrapper1);
            if (tfList.size() == 0) {
                fileInfoMapper.insert(fileInfo);
                response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
            } else {
                for (FileInfo info : tfList) {
                    if (info.getRefProjectId().equals(fileInfo.getRefProjectId())) {
                        QueryWrapper<FileInfo> wrapper2 = new QueryWrapper<>();
                        wrapper2.eq(FileInfo.COL_ID, info.getId());
                        fileInfoMapper.delete(wrapper2);
                        fileInfoMapper.insert(fileInfo);
                        response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
                    }
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    /**
     * 查询文件列表
     *
     * @param file
     * @return
     */
    @Override
    public IPage<FileInfo> selectFileList(FileInfo file, Integer pageNum, Integer pageSize) {
        Page<FileInfo> queryPage = new Page<>(pageNum, pageSize);
        IPage<FileInfo> data = fileInfoMapper.selectFileList(queryPage, file);
        List<FileInfo> rows = data.getRecords().stream().map(row -> {
            FileInfo resRow = new FileInfo();
            BeanUtils.copyProperties(row, resRow);
            return resRow;
        }).collect(Collectors.toList());
        Page<FileInfo> page = new Page<>();
        page.setTotal(data.getTotal());
        page.setRecords(data.getRecords());
        return page;
    }

    @Override
    public Integer deleteFile(FileInfo fileInfo) {
        return fileInfoMapper.deleteById(fileInfo.getId());
    }
}