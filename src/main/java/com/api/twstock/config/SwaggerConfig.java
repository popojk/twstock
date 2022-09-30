package com.api.twstock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.transaction.Transactional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //UI url:
    //http://localhost:8080/swagger-ui/index.html

    @Bean
    public Docket swaggerSetting(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Investment Dashboard Documentation")
                .description("Investment Dashboard api info")
                .version("0.0")
                .build();
    }
}
