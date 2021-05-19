package com.wang.fileservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:45
 */

@Data
@NoArgsConstructor
@TableName(value = "chunk_info")
public class ChunkInfo {
    /**
     * id
     */
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private String id;

    /**
     * 块编号，从1开始
     */
    @TableField(value = "CHUNK_NUMBER")
    private Long chunkNumber;

    /**
     * 块大小
     */
    @TableField(value = "CHUNK_SIZE")
    private Long chunkSize;

    /**
     * 当前块大小
     */
    @TableField(value = "CURRENT_CHUNKSIZE")
    private Long currentChunkSize;

    /**
     * 文件标识
     */
    @TableField(value = "IDENTIFIER")
    private String identifier;

    /**
     * 文件名
     */
    @TableField(value = "FILENAME")
    private String filename;

    /**
     * 相对路径
     */
    @TableField(value = "RELATIVE_PATH")
    private String relativePath;

    /**
     * 总块数
     */
    @TableField(value = "TOTAL_CHUNKS")
    private Long totalChunks;

    /**
     * 总大小
     */
    @TableField(value = "TOTAL_SIZE")
    private Integer totalSize;

    /**
     * 文件类型
     */
    @TableField(value = "FILETYPE")
    private String filetype;

    /**
     * 块内容
     */
//    @TableField(exist = false)
    private MultipartFile upfile;

    public static final String COL_ID = "ID";

    public static final String COL_CHUNK_NUMBER = "CHUNK_NUMBER";

    public static final String COL_CHUNK_SIZE = "CHUNK_SIZE";

    public static final String COL_CURRENT_CHUNKSIZE = "CURRENT_CHUNKSIZE";

    public static final String COL_IDENTIFIER = "IDENTIFIER";

    public static final String COL_FILE_NAME = "FILENAME";

    public static final String COL_RELATIVE_PATH = "RELATIVE_PATH";

    public static final String COL_TOTAL_CHUNKS = "TOTAL_CHUNKS";

    public static final String COL_TOTAL_SIZE = "TOTAL_SIZE";

    public static final String COL_FILE_TYPE = "FILETYPE";

    public String getFileName() {
        return this.filename;
    }
}
