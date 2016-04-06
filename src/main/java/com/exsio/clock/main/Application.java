package com.exsio.clock.main;

import com.exsio.clock.util.SpringProfile;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;
import java.util.List;

@SpringBootApplication
@ComponentScan("com.exsio")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        List<String> profiles = Lists.newArrayList();
        if (!GraphicsEnvironment.isHeadless()) {
            profiles.add(SpringProfile.UI);
        }
        new SpringApplicationBuilder(Application.class)
                .profiles(profiles.toArray(new String[profiles.size()]))
                .headless(false)
                .web(true)
                .run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}