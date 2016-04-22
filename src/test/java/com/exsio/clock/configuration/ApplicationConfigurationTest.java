package com.exsio.clock.configuration;

import com.beust.jcommander.internal.Lists;
import com.exsio.clock.AbstractDisplayAwareTest;
import com.exsio.clock.configuration.ApplicationConfiguration;
import com.exsio.clock.configuration.support.AtmosphereArgumentResolver;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.BroadcasterFactory;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.naming.NamingException;
import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ApplicationConfigurationTest extends AbstractDisplayAwareTest {

    ApplicationConfiguration underTest = new ApplicationConfiguration();


    @Test
    public void testAdditionOfAtmosphereArgumentResolver() {
        List<HandlerMethodArgumentResolver> resolvers = Lists.newArrayList();
        underTest.addArgumentResolvers(resolvers);
        Assert.assertEquals(resolvers.size(), 1);
        Assert.assertTrue(resolvers.get(0) instanceof AtmosphereArgumentResolver);
    }

    @Test
    public void test_atmosphereframework() {
        AtmosphereFramework result = underTest.atmosphereFramework();
        assertNotNull(result);
    }

    @Test
    public void test_broadcasterFactory() {
        AtmosphereFramework atmosphereFramework = mock(AtmosphereFramework.class);
        AtmosphereConfig atmosphereConfig = mock(AtmosphereConfig.class);
        BroadcasterFactory broadcasterFactory = mock(BroadcasterFactory.class);
        when(atmosphereFramework.getAtmosphereConfig()).thenReturn(atmosphereConfig);
        when(atmosphereConfig.getBroadcasterFactory()).thenReturn(broadcasterFactory);

        BroadcasterFactory result = underTest.broadcasterFactory(atmosphereFramework);
        assertEquals(result, broadcasterFactory);
    }

    @Test
    public void test_jacksonConverter() {
        assertNotNull(underTest.jacksonHttpMessageConverter());
    }

    @Test
    public void test_servletRegistrationBean() {
        ServletRegistrationBean result = underTest.servletRegistrationBean();

        assertNotNull(result);
        assertEquals(result.getServletName(), "push");
        assertTrue(result.getUrlMappings().contains("*.push"));
        assertEquals(result.getInitParameters().size(), 5);
    }

    @Test
    public void test_addResourceHandlers() {
        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);
        when(registry.addResourceHandler(anyString())).thenReturn(registration);
        underTest.addResourceHandlers(registry);

        verify(registry, times(9)).addResourceHandler(anyString());
        verify(registration,times(9)).addResourceLocations(anyString());

        verifyNoMoreInteractions(registration);
        verifyNoMoreInteractions(registry);
    }

    @Test
    public void test_properties() throws IOException, NamingException {
        assertNotNull(underTest.properties());
    }

    @Test
    public void test_customize() {
        ConfigurableEmbeddedServletContainer container = mock(ConfigurableEmbeddedServletContainer.class);
        ArgumentCaptor<MimeMappings> mimeMappingsArgumentCaptor = ArgumentCaptor.forClass(MimeMappings.class);

        underTest.customize(container);

        verify(container).setMimeMappings(mimeMappingsArgumentCaptor.capture());
        assertEquals(mimeMappingsArgumentCaptor.getValue().get("json"), "application/manifest+json");

    }
}
