package api.javajuke.lib;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// This is a configuration class
@Configuration
// We want Spring to enable Spring MVC
@EnableWebMvc
@ComponentScan(basePackages = "api.javajuke")
@PropertySource("classpath:application.properties")
// We're telling the application to read properties from application.properties,
// which we have placed in the resources directory
public class WebConfig implements WebMvcConfigurer {
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000000);
        return multipartResolver;
    }
}
