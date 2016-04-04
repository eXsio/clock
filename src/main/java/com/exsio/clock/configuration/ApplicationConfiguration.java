package com.exsio.clock.configuration;

import com.exsio.clock.configuration.support.AtmosphereArgumentResolver;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MeteorServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

    private final static MeteorServlet meteor = new MeteorServlet();

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() throws IOException, NamingException {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public AtmosphereFramework atmosphereFramework() {
        return meteor.framework();
    }

    @Bean
    public BroadcasterFactory broadcasterFactory(AtmosphereFramework atmosphereFramework) {
        return atmosphereFramework.getAtmosphereConfig().getBroadcasterFactory();
    }

    @Bean
    public HttpMessageConverter jacksonHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ServletRegistrationBean servletRegistrationBean() {

        ServletRegistrationBean push = new ServletRegistrationBean(meteor, "*.push");
        push.setLoadOnStartup(2);
        push.setInitParameters(new HashMap<String, String>() {
            {
                put("org.atmosphere.servlet", "org.springframework.web.servlet.DispatcherServlet");
            }
        });
        push.setAsyncSupported(true);
        push.setName("push");
        return push;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AtmosphereArgumentResolver());
    }
}
