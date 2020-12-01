package org.yunzhong.CommonTest.util.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * InitializingBean: bean初始化成功后调用<br>
 * DisposableBean: bean 销毁前调用
 * 
 * @author yunzhong
 *
 */
@Component
public class BeanLifecycle implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean afterPropertiesSet.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy.");
    }

}
