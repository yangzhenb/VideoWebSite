package com.yangz.tank.config;

import com.yangz.tank.component.ServerStartListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MvcConfig {

    @Bean
    public ServletListenerRegistrationBean contextListener(){

        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(new ServerStartListener());
        return registrationBean;
    }
}
