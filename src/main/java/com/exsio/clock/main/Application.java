package com.exsio.clock.main;

import com.exsio.clock.ui.UI;
import com.exsio.clock.ui.loading.Loading;
import com.exsio.clock.util.SpringProfile;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan("com.exsio")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        Loading.show();
        List<String> profiles = getProfiles();
        new SpringApplicationBuilder(Application.class)
                .profiles(profiles.toArray(new String[profiles.size()]))
                .headless(false)
                .web(true)
                .run(args);
    }

    private static List<String> getProfiles() {
        List<String> profiles = Lists.newArrayList();
        if (UI.isAvailable()) {
            profiles.add(SpringProfile.UI);
        }
        return profiles;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}