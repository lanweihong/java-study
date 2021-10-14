package com.lanweihong.encrypt.controller;

import com.lanweihong.common.base.vo.JsonResult;
import com.lanweihong.encrypt.entity.BookDO;
import com.lanweihong.encrypt.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/10/13 16:25
 */
@RestController
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public JsonResult<List<BookDO>> queryAllBook() {
        List<BookDO> books = bookService.listAll();
        return JsonResult.ok(books);
    }
}
