package com.possible.imisconnect.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.possible.imisconnect")
@EnableWebMvc
public class SecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(authenticationFilter);
    }

}
