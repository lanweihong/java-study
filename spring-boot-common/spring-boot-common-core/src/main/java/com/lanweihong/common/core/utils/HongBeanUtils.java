package com.lanweihong.common.core.utils;

import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lanweihong
 * @date 2021/12/25 15:20
 */
public class HongBeanUtils {

    public static <DTO> List<DTO> doListToDtoList(List<?> doList, Class<DTO> dtoClass){
        return pojoListConvert(doList, dtoClass);
    }

    public static <VO> List<VO> dtoListToVoList(List<?> dtoList, Class<VO> voClass) {
        return pojoListConvert(dtoList, voClass);
    }

    public static <DTO> DTO doToDto(Object doEntity, Class<DTO> clazz) {
        return doToDto(doEntity, clazz, "");
    }

    public static <DTO> DTO doToDto(Object doEntity, Class<DTO> clazz, String... ignoreProperties) {
        if (null == doEntity || null == clazz) {
            return null;
        }
        return pojoConvert(doEntity, clazz, ignoreProperties);
    }

    public static <VO> VO dtoToVo(Object dtoEntity, Class<VO> voClass, String... ignoreProperties) {
        if (null == dtoEntity || null == voClass) {
            return null;
        }
        return pojoConvert(dtoEntity, voClass, ignoreProperties);
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        return pojoConvert(source, targetClass);
    }

    private static <T> List<T> pojoListConvert(List<?> sourceList, Class<T> targetClass){
        if (sourceList.size() == 0 || null == targetClass) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(item -> pojoConvert(item, targetClass)).collect(Collectors.toList());
    }

    private static <T> T pojoConvert(Object sourceEntity, Class<T> targetClass, String... ignoreProperties) {
        T targetInstance;
        try {
            targetInstance = targetClass.newInstance();
        } catch (IllegalAccessException | InstantiationException exception) {
            return null;
        }
        if (ignoreProperties.length > 0) {
            BeanUtils.copyProperties(sourceEntity, targetInstance, ignoreProperties);
        } else {
            BeanUtils.copyProperties(sourceEntity, targetInstance);
        }
        return targetInstance;
    }
}
