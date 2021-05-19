package com.wang.fileservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 王冰炜
 * @create 2021-05-19 22:49
 */

@Data
@NoArgsConstructor
@TableName(value = "FILE_INFO")
public class FileInfo {
    /**
     * ID
     */
    @TableId(value = "ID", type = IdType.ID_WORKER)
    private String id;

    /**
     * 文件名
     */
    @TableField(value = "FILENAME")
    private String filename;

    /**
     * 文件标识
     */
    @TableField(value = "IDENTIFIER")
    private String identifier;

    /**
     * 总大小
     */
    @TableField(value = "TOTAL_SIZE")
    private Long totalSize;
    @TableField(exist = false)
    private String totalSizeName;

    /**
     * 存储地址
     */
    @TableField(value = "LOCATION")
    private String location;

    /**
     * 文件类型
     */
    @TableField(value = "FILETYPE")
    private String filetype;

    /**
     * 文件所属
     */
    @TableField(value = "REF_PROJECT_ID")
    private String refProjectId;

    /**
     * 上传用户
     */
    @TableField(value = "UPLOAD_USER")
    private Integer uploadUser;

    /**
     * 上传时间
     */
    @TableField(value = "UPLOAD_TIME")
    private LocalDateTime uploadTime;
    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        if(1024*1024 > this.totalSize && this.totalSize >= 1024 ) {
            this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/1024) + "KB";
        }else if(1024*1024*1024 > this.totalSize && this.totalSize >= 1024*1024 ) {
            this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/(1024*1024)) + "MB";
        }else if(this.totalSize >= 1024*1024*1024 ) {
            this.totalSizeName = String.format("%.2f",this.totalSize.doubleValue()/(1024*1024*1024)) + "GB";
        }else {
            this.totalSizeName = this.totalSize.toString() + "B";
        }
    }

    public static final String COL_ID = "ID";

    public static final String COL_FILE_NAME = "FILENAME";

    public static final String COL_IDENTIFIER = "IDENTIFIER";

    public static final String COL_TOTAL_SIZE = "TOTAL_SIZE";

    public static final String COL_LOCATION = "LOCATION";

    public static final String COL_FILE_TYPE = "FILETYPE";

    public static final String COL_REF_PROJECT_ID = "REF_PROJECT_ID";

    public static final String COL_UPLOAD_USER = "UPLOAD_USER";

    public static final String COL_UPLOAD_TIME = "UPLOAD_TIME";

    public String getFileName() {
        return this.filename;
    }
}