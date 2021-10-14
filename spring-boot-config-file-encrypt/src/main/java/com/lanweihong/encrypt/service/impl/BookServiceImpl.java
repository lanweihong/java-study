package com.lanweihong.encrypt.service.impl;

import com.lanweihong.encrypt.dao.IBookDao;
import com.lanweihong.encrypt.entity.BookDO;
import com.lanweihong.encrypt.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/10/13 16:24
 */
@Service("bookService")
public class BookServiceImpl implements IBookService {

    private final IBookDao bookDao;

    @Autowired
    public BookServiceImpl(IBookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<BookDO> listAll() {
        return bookDao.selectAll();
    }

    @Override
    public int addBook(BookDO book) {
        return bookDao.insert(book);
    }
}
