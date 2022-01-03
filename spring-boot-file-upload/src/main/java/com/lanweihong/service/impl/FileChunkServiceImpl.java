package com.lanweihong.service.impl;

import com.lanweihong.common.base.enums.MessageEnum;
import com.lanweihong.common.core.exception.BusinessException;
import com.lanweihong.common.core.utils.HongBeanUtils;
import com.lanweihong.dao.IFileChunkDao;
import com.lanweihong.dto.FileChunkDTO;
import com.lanweihong.entity.FileChunkDO;
import com.lanweihong.param.FileChunkParam;
import com.lanweihong.service.IFileChunkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author lanweihong
 */
@Service(value = "fileChunkService")
public class FileChunkServiceImpl implements IFileChunkService {

    private final IFileChunkDao fileChunkDao;

    @Autowired
    public FileChunkServiceImpl(IFileChunkDao fileChunkDao) {
        this.fileChunkDao = fileChunkDao;
    }

    @Override
    public List<FileChunkDTO> listByFileMd5(String md5) {
        List<FileChunkDO> fileChunks = this.fileChunkDao.listByMd5(md5);
        if (fileChunks.size() == 0) {
            return Collections.emptyList();
        }
        return HongBeanUtils.doListToDtoList(fileChunks, FileChunkDTO.class);
    }

    @Override
    public void saveFileChunk(FileChunkParam param) {
        FileChunkDO fileChunkDo = null;
        if (!param.isNew()) {
            fileChunkDo = this.fileChunkDao.selectByPrimaryKey(param.getId());
            if (null == fileChunkDo) {
                throw new BusinessException(MessageEnum.RECORD_NOT_EXISTED);
            }
        }
        if (null == fileChunkDo) {
            fileChunkDo = new FileChunkDO();
        } else {
            fileChunkDo.setUpdateTime(LocalDateTime.now());
        }
        BeanUtils.copyProperties(param, fileChunkDo, "id");
        fileChunkDo.setFileName(param.getFilename());
        int result;
        if (param.isNew()) {
            result = this.fileChunkDao.insert(fileChunkDo);
        } else {
            result = this.fileChunkDao.updateByPrimaryKeySelective(fileChunkDo);
        }
        if (0 == result) {
            throw new BusinessException(MessageEnum.FAIL);
        }
        HongBeanUtils.doToDto(fileChunkDo, FileChunkDTO.class);
    }

    @Override
    public int deleteById(Long id) {
       FileChunkDO fileChunkDo = this.fileChunkDao.selectByPrimaryKey(id);
       if (null == fileChunkDo) {
         throw new BusinessException(MessageEnum.RECORD_NOT_EXISTED);
       }
       return this.fileChunkDao.delete(fileChunkDo);
    }

    @Override
    public int delete(FileChunkDO fileChunk) {
        return this.fileChunkDao.delete(fileChunk);
    }
}
