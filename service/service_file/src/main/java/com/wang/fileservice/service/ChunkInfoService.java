package com.wang.fileservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.fileservice.entity.ChunkInfo;
import com.wang.fileservice.response.ChunkResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:54
 */

public interface ChunkInfoService extends IService<ChunkInfo> {
    /**
     * 校验当前文件
     *
     * @param chunkInfo
     * @param response
     * @return 秒传？续传？新传？
     */
    ChunkResult checkChunkState(ChunkInfo chunkInfo, HttpServletResponse response);

    /**
     * 上传文件
     *
     * @param chunk
     * @return
     */
    Integer uploadFile(ChunkInfo chunk);
}
