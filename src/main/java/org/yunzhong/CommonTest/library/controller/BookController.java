package org.yunzhong.CommonTest.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.library.service.BookService;
import org.yunzhong.CommonTest.model.DefaultBeanFactory;
import org.yunzhong.CommonTest.model.DefaultBeanFactory.DefaultBean;

@RestController
@RequestMapping("/library/book")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @RequestMapping("list")
    public DefaultBean search() {
        return DefaultBeanFactory.ok(null);
    }
}
