package com.exsio.clock.service.publisher;


import com.exsio.clock.model.PushMessage;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.push.PushService;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;

public class PushTimeInfoPublisherTest {

    @Mock
    PushService pushService;

    PushTimeInfoPublisherImpl underTest;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new PushTimeInfoPublisherImpl(pushService);
    }

    @Test
    public void test_publish() {

        TimeInfo time = new TimeInfo("00:00", true, true);
        underTest.publish(time);
        ArgumentCaptor<PushMessage> messageCaptor = ArgumentCaptor.forClass(PushMessage.class);
        verify(pushService).push(matches(PushTimeInfoPublisherImpl.CLOCK_CHANNEL), messageCaptor.capture());
        TimeInfo timeResult = (TimeInfo) messageCaptor.getValue().getObject();
        assertEquals(time.getTime(), timeResult.getTime());
        assertEquals(time.isAlert(), timeResult.isAlert());
        assertEquals(time.isClockStarted(), timeResult.isClockStarted());
    }
}
