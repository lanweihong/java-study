package com.lanweihong.param;

import com.lanweihong.common.base.param.BaseParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分片上传参数
* @author lanweihong
*/
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class FileChunkParam extends BaseParam {

    @NotNull(message = "当前分片不能为空")
    private Integer chunkNumber;

    @NotNull(message = "分片大小不能为空")
    private Float chunkSize;

    @NotNull(message = "当前分片大小不能为空")
    private Float currentChunkSize;

    @NotNull(message = "文件总数不能为空")
    private Integer totalChunks;

    @NotBlank(message = "文件标识不能为空")
    private String identifier;

    @NotBlank(message = "文件名不能为空")
    private String filename;

    private String fileType;

    private String relativePath;

    @NotNull(message = "文件总大小不能为空")
    private Float totalSize;

    private MultipartFile file;
}
