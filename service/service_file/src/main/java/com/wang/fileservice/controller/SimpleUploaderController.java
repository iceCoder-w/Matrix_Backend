package com.wang.fileservice.controller;

import com.wang.fileservice.entity.ChunkInfo;
import com.wang.fileservice.response.ChunkResult;
import com.wang.fileservice.service.ChunkInfoService;
import com.wang.fileservice.service.FileInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 王冰炜
 * @create 2021-05-18 19:26
 */

@Api(tags = "文件上传")
@RestController
@RequestMapping("/fileservice/simupload")
@Slf4j
@CrossOrigin
public class SimpleUploaderController {

    private final static String utf8 = "utf-8";
    @Autowired
    private ChunkInfoService chunkInfoService;
    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 校验文件
     *
     * @param chunk
     * @param response
     * @return
     */
    @GetMapping("/uploadFile")
    public ChunkResult checkChunk(ChunkInfo chunk, HttpServletResponse response) {
//        log.info("校验文件：{}", chunk);
        System.out.println("第一次："+chunk);
        return chunkInfoService.checkChunkState(chunk, response);
    }

    /**
     * 文件块上传
     *
     * @param chunk
     * @return
     */
//    @PostMapping("/uploadFile")
//    public Integer uploadChunk(ChunkInfo chunk) {
//        System.out.println("第二次："+chunk);
//        return chunkInfoService.uploadFile(chunk);
//    }



    @RequestMapping("/uploadFile")
    @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding(utf8);
        Integer schunk = null;
        Integer schunks = null;
        String name = null;
        String uploadPath = "C:\\fileItem";

        BufferedOutputStream os = null;

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(2048); // 设置缓冲区
            factory.setRepository(new File(uploadPath)); // 设置保存的地址

            // 使用upload解析requset
            ServletFileUpload upload = new ServletFileUpload(factory);
//            System.out.println("upload.parseRequest(request)"+upload.parseRequest(request));
            // 设置文件大小
            upload.setFileSizeMax(10241*10241);
            upload.setSizeMax(10241*10241);
            List<FileItem> items = upload.parseRequest(request);

            // 遍历表单信息
            for (FileItem item : items) {
                if (item.isFormField()) { // 表单域，非文件域
                    if ("chunkNumber".equals(item.getFieldName())) {
                        schunk = Integer.parseInt(item.getString(utf8));
                    }
                    if ("totalChunks".equals(item.getFieldName())) {
                        schunks = Integer.parseInt(item.getString(utf8));
                    }
                    if ("filename".equals(item.getFieldName())) {
                        name = item.getString(utf8);
                    }
                }
            }
            System.out.println("chunk: "+schunk+" name: "+name+" chunks: "+schunks);
            // 遍历文件域信息
            for (FileItem item : items){
                if (!item.isFormField()){ // 文件域
                    String temFileName = name;
                    if (name != null){
                        if (schunk != null){
                            temFileName = schunk + "_" + name;
                            System.out.println(schunk);
                        }
                        File tempFile = new File(uploadPath, temFileName);
                        if (!tempFile.exists()){ // 当某个分片不存在时，才需要写入（断点续传）
                            item.write(tempFile);
                        }
                    }
                }

            }
            // 判断是否有分片 及 是否是最后一个分片
            if (schunk != null && schunk.intValue() == schunks.intValue()){
                File tempFile = new File(uploadPath, name);
                os = new BufferedOutputStream(new FileOutputStream(tempFile));
                // 合并分片，需要先判断每个分片是否都在
                for (int i = 1; i <= schunks; i++) {
                    File file = new File(uploadPath, i + "_" + name);
                    while (!file.exists()){
                        Thread.sleep(100);
                    }
                    // 将所有分片读入数组
                    byte[] bytes = FileUtils.readFileToByteArray(file);
                    os.write(bytes);
                    os.flush();
                    file.delete();
                }
                os.flush();
            }
            response.getWriter().write("上传成功：" + name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null){
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
