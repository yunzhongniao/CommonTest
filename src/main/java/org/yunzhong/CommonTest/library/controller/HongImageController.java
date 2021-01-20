package org.yunzhong.CommonTest.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.library.service.HongImageService;

@RestController
@RequestMapping("/library/image")
public class HongImageController {

    @Autowired
    private HongImageService imageService;
    
}
