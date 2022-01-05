package com.lanweihong.common.base.param;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lanweihong
 * @date 2021/12/25 15:10
 */
@Getter
@Setter
public class BaseParam {
    private Long id;

    public boolean isNew() {
        return null == this.id;
    }
}
