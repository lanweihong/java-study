package com.lanweihong.service.impl;

import com.lanweihong.common.base.enums.MessageEnum;
import com.lanweihong.common.core.exception.BusinessException;
import com.lanweihong.param.FileChunkParam;
import com.lanweihong.service.IFileChunkService;
import com.lanweihong.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.Cleaner;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author lanweihong
 * @date 2021/12/23 3:38
 */
@Service("fileService")
@Slf4j
public class FileServiceImpl implements IFileService {

    /**
     * 默认的分片大小：20MB
     */
    public static final long DEFAULT_CHUNK_SIZE = 20 * 1024 * 1024;

    public static final String BASE_FILE_SAVE_PATH = "d:/UploadFile/986310747";

    private final IFileChunkService fileChunkService;

    @Autowired
    public FileServiceImpl(IFileChunkService fileChunkService) {
        this.fileChunkService = fileChunkService;
    }

    @Override
    public boolean uploadFile(FileChunkParam param) {
        if (null == param.getFile()) {
            throw new BusinessException(MessageEnum.UPLOAD_FILE_NOT_NULL);
        }
        // 判断目录是否存在，不存在则创建目录
        File savePath = new File(BASE_FILE_SAVE_PATH);
        if (!savePath.exists()) {
            boolean flag = savePath.mkdirs();
            if (!flag) {
                log.error("保存目录创建失败");
                return false;
            }
        }
        // 这里可以使用 uuid 来指定文件名，上传完成后再重命名
        String fullFileName = savePath + File.separator + param.getFilename();
        // 单文件上传
        if (param.getTotalChunks() == 1) {
            return uploadSingleFile(fullFileName, param);
        }
        // 分片上传，这里使用 uploadFileByRandomAccessFile 方法，也可以使用 uploadFileByMappedByteBuffer 方法上传
        boolean flag = uploadFileByRandomAccessFile(fullFileName, param);
        if (!flag) {
            return false;
        }
        // 保存分片上传信息
        fileChunkService.saveFileChunk(param);
        return true;
    }

    private boolean uploadFileByRandomAccessFile(String resultFileName, FileChunkParam param) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(resultFileName, "rw")) {
            // 分片大小必须和前端匹配，否则上传会导致文件损坏
            long chunkSize = param.getChunkSize() == 0L ? DEFAULT_CHUNK_SIZE : param.getChunkSize().longValue();
            // 偏移量
            long offset = chunkSize * (param.getChunkNumber() - 1);
            // 定位到该分片的偏移量
            randomAccessFile.seek(offset);
            // 写入
            randomAccessFile.write(param.getFile().getBytes());
        } catch (IOException e) {
            log.error("文件上传失败：" + e);
            return false;
        }
        return true;
    }

    private boolean uploadFileByMappedByteBuffer(String resultFileName, FileChunkParam param) {
        // 分片上传
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(resultFileName, "rw");
             FileChannel fileChannel = randomAccessFile.getChannel()) {
            // 分片大小必须和前端匹配，否则上传会导致文件损坏
            long chunkSize = param.getChunkSize() == 0L ? DEFAULT_CHUNK_SIZE : param.getChunkSize().longValue();
            // 写入文件
            long offset = chunkSize * (param.getChunkNumber() -1);
            byte[] fileBytes = param.getFile().getBytes();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileBytes.length);
            mappedByteBuffer.put(fileBytes);
            // 释放
            unmap(mappedByteBuffer);
        } catch (IOException e) {
            log.error("文件上传失败：" + e);
            return false;
        }
        return true;
    }

    private boolean uploadSingleFile(String resultFileName, FileChunkParam param) {
        File saveFile = new File(resultFileName);
        try {
            // 写入
            param.getFile().transferTo(saveFile);
        } catch (IOException e) {
            log.error("文件上传失败：" + e);
            return false;
        }
        return true;
    }

    /**
     * 释放 MappedByteBuffer
     * 在 MappedByteBuffer 释放后再对它进行读操作的话就会引发 jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是 crash 就发生了。所以为了系统稳定性释放前一般需要检
     * 查是否还有线程在读或写
     * 来源：https://my.oschina.net/feichexia/blog/212318
     * @param mappedByteBuffer mappedByteBuffer
     */
    public static void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }
            mappedByteBuffer.force();
            AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                try {
                    Method getCleanerMethod = mappedByteBuffer.getClass()
                            .getMethod("cleaner");
                    getCleanerMethod.setAccessible(true);
                    Cleaner cleaner =
                            (Cleaner) getCleanerMethod
                                    .invoke(mappedByteBuffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    log.error("MappedByteBuffer 释放失败：" + e);
                }
                System.out.println("clean MappedByteBuffer completed");
                return null;
            });
        } catch (Exception e) {
            log.error("unmap error:" + e);
        }
    }
}
