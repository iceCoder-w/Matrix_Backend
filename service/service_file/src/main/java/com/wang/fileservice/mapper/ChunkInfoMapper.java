package com.wang.fileservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.fileservice.entity.ChunkInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:51
 */

@Mapper
public interface ChunkInfoMapper extends BaseMapper<ChunkInfo> {
    /**
     * 查询文件块号
     * @param record
     * @return
     */
    ArrayList<Integer> selectChunkNumbers(ChunkInfo record);
}
