package org.ruanwei.demo.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author ruanwei
 *
 */
@Configuration
@EnableWebFlux
@ComponentScan("org.ruanwei.demo")
public class WebAppConfig implements WebFluxConfigurer {

}
