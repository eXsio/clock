package com.exsio.clock.configuration;

import com.beust.jcommander.internal.Lists;
import com.exsio.clock.configuration.ApplicationConfiguration;
import com.exsio.clock.configuration.support.AtmosphereArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ApplicationConfigurationTest {

    ApplicationConfiguration underTest = new ApplicationConfiguration();


    @Test
    public void testAdditionOfAtmosphereArgumentResolver() {
        List<HandlerMethodArgumentResolver> resolvers = Lists.newArrayList();
        underTest.addArgumentResolvers(resolvers);
        Assert.assertEquals(resolvers.size(), 1);
        Assert.assertTrue(resolvers.get(0) instanceof AtmosphereArgumentResolver);
    }
}
