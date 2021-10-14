package com.lanweihong.encrypt.service;

import com.lanweihong.encrypt.entity.BookDO;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/10/13 16:23
 */
public interface IBookService {

    /**
     * 查询所有
     * @return
     */
    List<BookDO> listAll();

    /**
     * 添加书籍
     * @param book book
     * @return
     */
    int addBook(BookDO book);
}
