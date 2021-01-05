package org.yunzhong.CommonTest.util.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
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
public class MyBeanLifecycle implements InitializingBean, DisposableBean, BeanFactoryAware, BeanNameAware {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("bean lifecycle: InitializingBean afterPropertiesSet.");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("bean lifecycle: DisposableBean destroy.");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("bean lifecycle: BeanFactoryAware.");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("bean lifecycle: BeanNameAware.");
    }

}
