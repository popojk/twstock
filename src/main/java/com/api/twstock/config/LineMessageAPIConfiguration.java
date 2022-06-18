package com.api.twstock.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineMessageAPIConfiguration {

    @Bean
    public RequestInterceptor requestTokenBearerInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer FPZY7+S5Wjzs6+sRYK6b4UP6/TOby16TSUFES0FoUuUV3DYGVkDdRaqmmiauRWj8OCpFNQNQ69IgsFzonfcdzZ4xueZIGnwaf80gqH+sOQ8SygTOzdv3h2LBRvcgx1OC19YQpFicY4joMRrtDG7lNgdB04t89/1O/w1cDnyilFU=");
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
