package com.exsio.clock.service;

import com.exsio.clock.model.PushMessage;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PushServiceTest {

    private PushService underTest;

    @Mock
    private BroadcasterFactory broadcasterFactory;

    @Mock
    private Broadcaster broadcaster;

    @Mock
    private MessageSerializer messageSerializer;

    private final static String TEST_CHANNEL = "TEST_CHANNEL";
    private final static PushMessage TEST_OBJECT = new PushMessage("TEST_OBJEC", new Object());

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void resetMocks() {
        Mockito.reset(broadcaster, broadcasterFactory);
        when(broadcasterFactory.lookup(anyString(), anyBoolean())).thenReturn(broadcaster);
        underTest = new PushServiceImpl(broadcasterFactory, messageSerializer);
    }

    @Test
    public void test_push_to_all() {
        underTest.push(TEST_CHANNEL, TEST_OBJECT);
        verify(broadcaster, times(1)).broadcast(any());
        verify(broadcasterFactory, times(1)).lookup(TEST_CHANNEL, true);
    }
}
