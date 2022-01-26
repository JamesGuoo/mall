package io.james.mall.config;

import io.james.mall.config.handler.TokenToUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MallWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private TokenToUserMethodArgumentResolver tokenToMallUserMethodArgumentResolver;

    /**
     * @param argumentResolvers
     * @tip @TokenToMallUser @TokenToAdminUser 注解处理方法
     */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenToMallUserMethodArgumentResolver);
    }
}
