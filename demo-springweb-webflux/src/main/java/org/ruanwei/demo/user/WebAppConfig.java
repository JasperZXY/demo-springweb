package org.ruanwei.demo.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ruanwei
 *
 */
@Configuration
@EnableWebMvc
// @ComponentScan("org.example.web")
public class WebAppConfig implements WebMvcConfigurer {

}
