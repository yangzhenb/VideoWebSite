package com.yangz.tank.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    /*
        因为在application.yml中配置的druid属性在datasourceproperties.class文件中没有对应属性，所以无法自动加载
        所以需要手动创建druid组件，取代容器通过反射自动创建，通过ConfigurationProperties注解来读取加载配置文件
        中的druid默认属性
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }

    /*
        配置Druid监控
            1、配置一个管理后台的Servlet
            2、配置一个监控的Filter
            项目启动后访问localhost:8080/druid/来登陆Druid管理页面
     */
    //    1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");
        initParams.put("allow", "");//允许谁来访问，不写的话默认允许所有人访问
        initParams.put("deny", "192.168.1.222");//拒绝该ip地址访问
        //设置初始化参数
        bean.setInitParameters(initParams);
        return  bean;
    }

    //2、配置一个监控的Filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        //配置那些请求不拦截
        initParams.put("exclusions", "*.js,*.css,*.flv,*.jpg,/druid/*");
        bean.setInitParameters(initParams);
        //拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}
