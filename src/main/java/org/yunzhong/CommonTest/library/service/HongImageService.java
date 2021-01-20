package org.yunzhong.CommonTest.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HongImageService {

    @Value("${common.storage.image.path:.}")
    private String imagePath;
}
