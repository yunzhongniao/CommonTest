package org.yunzhong.CommonTest.util.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

/**
 * InitializingBean: bean初始化成功后调用<br>
 * DisposableBean: bean 销毁前调用
 * 
 * @author yunzhong
 *
 */
@Component
public class MyBeanLifecycleJSR {

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        System.out.println("jsr bean lifecycle: @PostConstruct.");
    }

    @PreDestroy
    public void destroy() throws Exception {
        System.out.println("jsr bean lifecycle: @PreDestroy.");
    }

}
