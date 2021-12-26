package com.alpha.common.swagger;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * Swagger2 配置类
 * 生产环境禁用Swagger，SpringBoot环境下选其一即可
 * //@Profile({"dev", "test"})
 * //@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
 */
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration {

    @Value("${swagger.title}")
    String SWAGGER_TITLE;

    @Value("${swagger.description}")
    String SWAGGER_DESCRIPTION;

    @Value("${swagger.service.url}")
    String SWAGGER_SERVICE_URL;

    @Value("${swagger.version}")
    String SWAGGER_VERSION;

    @Value("${swagger.enable:false}")
    boolean SWAGGER_ENABLE;

    @Bean
    public Docket createRestApi() {
        //设置请求头参数
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(SWAGGER_ENABLE)
                .apiInfo(apiInfo()).securitySchemes(Arrays.asList(apiKey()))
                //.genericModelSubstitutes(ResponseEntity.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    //logintoken
    private ApiKey apiKey() {
        return new ApiKey("logintoken", "logintoken", "header");
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("alpha", SWAGGER_SERVICE_URL, "");
        return new ApiInfoBuilder().title(SWAGGER_TITLE)
                .description(SWAGGER_DESCRIPTION)
                .contact(contact)
                .version(SWAGGER_VERSION).build();
    }

}
