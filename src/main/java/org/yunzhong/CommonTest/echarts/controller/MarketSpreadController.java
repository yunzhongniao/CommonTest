package org.yunzhong.CommonTest.echarts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunzhong.CommonTest.echarts.service.MarketSpreadService;
import org.yunzhong.CommonTest.model.DefaultBeanFactory;
import org.yunzhong.CommonTest.model.DefaultBeanFactory.DefaultBean;

@RestController
@RequestMapping("/market/spread")
public class MarketSpreadController {

    @Autowired
    private MarketSpreadService marketSpreadService;
    
    @RequestMapping("list")
    public DefaultBean search() {
        return DefaultBeanFactory.ok(marketSpreadService.getData());
    }
}
