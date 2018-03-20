package org.ruanwei.demo.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @EnableWebMvc注解导入了DelegatingWebMvcConfiguration来提供Spring MVC的默认Spring配置，并且检测和委派给WebMvcConfigurer来自定义配置。
 * 对于配置API中没有的高级定制，可以去掉@EnableWebMvc，直接继承DelegatingWebMvcConfiguration
 * @author ruanwei
 *
 */
@Configuration
@EnableWebMvc
// @ComponentScan("org.example.web")
public class WebAppConfig implements WebMvcConfigurer {

}
