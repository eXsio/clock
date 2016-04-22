package com.exsio.clock.controller;


import com.exsio.clock.AbstractDisplayAwareTest;
import com.exsio.clock.exception.PushServiceRuntimeException;
import com.exsio.clock.service.push.PushService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PushControllerTest extends AbstractDisplayAwareTest {

    private final static long MAX_RETRIES = 3L;

    private PushController underTest;

    @Mock
    private PushService pushService;

    @Mock
    private BroadcasterFactory broadcasterFactory;

    @Mock
    private Broadcaster broadcaster;

    @Mock
    private AtmosphereResource atmosphereResource;

    private final static String TEST_CHANNEL = "TEST_CHANNEL";
    private final static long SUSPEND_TIME = -1L;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(broadcasterFactory.lookup(anyString(), anyBoolean())).thenReturn(broadcaster);
        when(atmosphereResource.resumeOnBroadcast(anyBoolean())).thenReturn(atmosphereResource);
        underTest = new PushController(broadcasterFactory);
        underTest.setMaxRetries(MAX_RETRIES);
    }

    @Test
    public void test_subscribe_successful() {
        underTest.subscribe(atmosphereResource, TEST_CHANNEL);
        verify(broadcaster, times(1)).addAtmosphereResource(atmosphereResource);
        verify(atmosphereResource, times(1)).suspend(SUSPEND_TIME);
    }

    @Test(expectedExceptions = PushServiceRuntimeException.class)
    public void test_subscribe_unsuccessful() {
        when(broadcaster.addAtmosphereResource(atmosphereResource)).thenThrow(new NullPointerException());
        underTest.subscribe(atmosphereResource, TEST_CHANNEL);
        reset(broadcaster);
    }

}
