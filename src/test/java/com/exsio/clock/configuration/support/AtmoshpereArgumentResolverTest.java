package com.exsio.clock.configuration.support;

import com.exsio.clock.AbstractDisplayAwareTest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.FrameworkConfig;
import org.atmosphere.cpr.Meteor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;

public class AtmoshpereArgumentResolverTest extends AbstractDisplayAwareTest {

    AtmosphereArgumentResolver underTest = new AtmosphereArgumentResolver();

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private AtmosphereResource atmosphereResource;

    @Mock
    private ModelAndViewContainer modelAndViewContainer;

    @Mock
    private NativeWebRequest nativeWebRequest;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private WebDataBinderFactory webDataBinderFactory;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(methodParameter.getParameterType()).thenReturn((Class) AtmosphereResource.class);
        when(nativeWebRequest.getNativeRequest(HttpServletRequest.class)).thenReturn(httpServletRequest);
        when(httpServletRequest.getAttribute(FrameworkConfig.ATMOSPHERE_RESOURCE)).thenReturn(atmosphereResource);
    }

    @Test
    public void testSupportsParameter() {
        Assert.assertTrue(underTest.supportsParameter(methodParameter));
    }

    @Test
    public void testResolveArgument() throws Exception {
        Object result = underTest.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
        Assert.assertTrue(result instanceof AtmosphereResource);
        Assert.assertEquals(result, atmosphereResource);
    }

    @Test
    public void getGetMeteor() {
        Meteor meteor = underTest.getMeteor(nativeWebRequest);
        Assert.assertNotNull(meteor);
        Assert.assertEquals(meteor.getAtmosphereResource(), atmosphereResource);
    }
}
