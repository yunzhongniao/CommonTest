package org.yunzhong.CommonTest;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author yunzhong
 *
 */
@Component
public class MyResourceLoader {

	@Autowired
	private ConfigurableEnvironment environment;

	@PostConstruct
	public void postProcessEnvironment() {
		PropertiesPropertySourceLoader propertiesPropertySourceLoader = new PropertiesPropertySourceLoader();
		ClassPathResource classPathResource = new ClassPathResource("myConfig/config.properties");
		try {
			List<PropertySource<?>> properties = propertiesPropertySourceLoader.load("custom", classPathResource);
			for (PropertySource<?> property : properties) {
				environment.getPropertySources().addLast(property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		environment.getPropertySources().forEach(source -> {
			System.out.println("all resources:" + source.getName() + " | value: " + source.getSource());
		});
		PropertySource<?> propertySource = environment.getPropertySources().get("custom");
		Object property = propertySource.getProperty("yunzhong.config.mine");
		System.out.println("load resource yunzhong.config: " + environment.getProperty("yunzhong.config"));
		System.out.println("load resource yunzhong.config.mine: "
				+ environment.getProperty("yunzhong.config.mine"));
	}
}
