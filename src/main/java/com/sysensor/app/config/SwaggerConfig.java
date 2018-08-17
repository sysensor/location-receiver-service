package com.sysensor.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
//@Import({ springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration.class})
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sysensor.app"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket productApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("All-APIs")
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.sysensor.app"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "GPS-Coordinates-Receiver",
                "GPS points receiver",
                "v1",
                "Sysensor Vehicle Tracking System",
                new Contact("Sysensor IT Solutions", "https://medium.com/complex-to-simple", "sysensor.it@gmail.com"),
                "License of MAIN_PATH_API", "MAIN_PATH_API license URL", new ArrayList<>());
    }

}
