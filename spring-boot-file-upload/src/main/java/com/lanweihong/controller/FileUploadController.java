package com.lanweihong.controller;

import com.lanweihong.common.base.enums.MessageEnum;
import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.dto.FileChunkDTO;
import com.lanweihong.param.FileChunkParam;
import com.lanweihong.service.IFileChunkService;
import com.lanweihong.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lanweihong
 * @date 2021/12/25 15:40
 */
@RestController
@Slf4j
public class FileUploadController {

    private final IFileService fileService;

    private final IFileChunkService fileChunkService;

    @Autowired
    public FileUploadController(IFileService fileService, IFileChunkService fileChunkService) {
        this.fileService = fileService;
        this.fileChunkService = fileChunkService;
    }

    @GetMapping("/upload")
    public JsonResult<Map<String, Object>> checkUpload(@Valid FileChunkParam param) {
        log.info("文件MD5:" + param.getIdentifier());
        List<FileChunkDTO> list = fileChunkService.listByFileMd5(param.getIdentifier());
        Map<String, Object> data = new HashMap<>(1);
        if (list.size() == 0) {
            data.put("uploaded", false);
            return JsonResult.ok(data);
        }
        // 处理单文件
        if (list.get(0).getTotalChunks() == 1) {
            data.put("uploaded", true);
            // todo 返回 url
            data.put("url", "");
            return JsonResult.ok(data);
        }
        // 处理分片
        int[] uploadedFiles = new int[list.size()];
        int index = 0;
        for (FileChunkDTO fileChunkItem : list) {
            uploadedFiles[index] = fileChunkItem.getChunkNumber();
            index++;
        }
        data.put("uploadedChunks", uploadedFiles);
        return JsonResult.ok(data);
    }

    @PostMapping("/upload")
    public JsonResult<String> chunkUpload(@Valid FileChunkParam param) {
        boolean flag = fileService.uploadFile(param);
        if (!flag) {
            return JsonResult.error(MessageEnum.FAIL);
        }
        return JsonResult.ok();
    }

}
