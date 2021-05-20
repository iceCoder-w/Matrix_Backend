package com.wang.fileservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.fileservice.entity.ChunkInfo;
import com.wang.fileservice.mapper.ChunkInfoMapper;
import com.wang.fileservice.response.ChunkResult;
import com.wang.fileservice.service.ChunkInfoService;
import com.wang.fileservice.util.FileInfoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:55
 */


@Service
@Slf4j
public class ChunkInfoServiceImpl extends ServiceImpl<ChunkInfoMapper, ChunkInfo> implements ChunkInfoService {
    @Value("${base.file-path}")
    private String uploadFolder;
    @Autowired
    private ChunkInfoMapper chunkInfoMapper;

    /**
     * 校验当前文件
     * @param chunkInfo
     * @param response
     * @return 秒传？续传？新传？
     */
    @Override
    public ChunkResult checkChunkState(ChunkInfo chunkInfo, HttpServletResponse response) {
        ChunkResult chunkResult = new ChunkResult();
        // todo:多一次判断文件大小
        String path = "C:\\fileItem\\" + chunkInfo.getFileName();
        File f = new File(path);
        if (f.exists() && f.isFile()){
            System.out.println("已经存在的文件的大小："+f.length());
        }
        System.out.println("前端传来的文件的大小："+chunkInfo.getTotalSize());
        if(FileInfoUtils.fileExists(path) && chunkInfo.getTotalSize()==f.length()) {
            chunkResult.setSkipUpload(true);
            chunkResult.setLocation(path);
            response.setStatus(HttpServletResponse.SC_OK);
            chunkResult.setMessage(chunkInfo.getFileName() + "已一键秒传~");
            return chunkResult;
        }
        ArrayList<Integer> list = chunkInfoMapper.selectChunkNumbers(chunkInfo);
        if (list !=null && list.size() > 0) {
            chunkResult.setSkipUpload(false);
            chunkResult.setUploadedChunks(list);
            response.setStatus(HttpServletResponse.SC_OK);
            chunkResult.setMessage("部分文件块已存在，继续上传剩余文件块，实现断点续传");
            return chunkResult;
        }
        System.out.println("第一次上传文件："+chunkResult);
        return chunkResult;
    }

    /**
     * 写文件
     * @param chunk
     * @return
     */
    @Override
    public Integer uploadFile(ChunkInfo chunk) {
        Integer apiRlt = HttpServletResponse.SC_OK;
        MultipartFile file = chunk.getContents();
        System.out.println("file:"+file);
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FileInfoUtils.generatePath(uploadFolder, chunk));
            Files.write(path, bytes);
            if(chunkInfoMapper.insert(chunk) < 0){
                apiRlt = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
            }
        } catch (IOException e) {
            log.error("写文件出错：{}",e.getMessage());
            apiRlt = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        }
        return apiRlt;
    }
}
