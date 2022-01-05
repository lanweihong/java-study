package com.lanweihong.minio.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.druid.util.StringUtils;
import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.minio.dto.MinioOperationResult;
import com.lanweihong.minio.dto.MinioUploadInfo;
import com.lanweihong.minio.param.GetMinioUploadInfoParam;
import com.lanweihong.minio.param.MergeMinioMultipartParam;
import com.lanweihong.minio.service.IFileService;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lanweihong
 * @date 2022/1/4 17:36
 */
@RestController
public class MinioController {

    private final IFileService fileService;

    @Autowired
    public MinioController(IFileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 获取上传 url
     * @param param 参数
     * @return
     */
    @PostMapping("/upload")
    public JsonResult<MinioUploadInfo> getUploadId(@Validate @RequestBody GetMinioUploadInfoParam param) {
        MinioUploadInfo minioUploadId = fileService.getUploadId(param);
        return JsonResult.ok(minioUploadId);
    }

    /**
     * 校验文件是否存在
     * @param md5 文件 md5
     * @return
     */
    @GetMapping("/upload/check")
    public JsonResult<MinioOperationResult> checkFileUploadedByMd5(@RequestParam("md5")String md5) {
        MinioOperationResult result = fileService.checkFileExistsByMd5(md5);
        return JsonResult.ok(result);
    }

    /**
     * 合并文件
     * @param param
     * @return
     */
    @PostMapping("/upload/merge")
    public JsonResult<JSONObject> mergeUploadFile(@Valid MergeMinioMultipartParam param) {
        String result = fileService.mergeMultipartUpload(param);
        if (StringUtils.isEmpty(result)) {
            return JsonResult.error("合并失败");
        }
        JSONObject object = new JSONObject();
        object.set("url", result);
        return JsonResult.ok(object);
    }

}
