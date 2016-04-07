package com.exsio.clock.service.publisher;


import com.exsio.clock.event.TimeChangedEvent;
import com.exsio.clock.model.TimeInfo;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.testng.Assert.assertEquals;

public class SpringTimeInfoPublisherTest {

    @Mock
    ApplicationEventPublisher eventPublisher;

    SpringTimeInfoPublisherImpl underTest;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new SpringTimeInfoPublisherImpl(eventPublisher);
    }

    @Test
    public void test_publish() {

        TimeInfo time = new TimeInfo("00:00", "01:00", true, true);
        underTest.publish(time);
        ArgumentCaptor<TimeChangedEvent> messageCaptor = ArgumentCaptor.forClass(TimeChangedEvent.class);
        verify(eventPublisher).publishEvent(messageCaptor.capture());
        verifyNoMoreInteractions(eventPublisher);
        TimeInfo timeResult = messageCaptor.getValue().getObject();
        assertEquals(time.getTime(), timeResult.getTime());
        assertEquals(time.getBoundary(), timeResult.getBoundary());
        assertEquals(time.isAlert(), timeResult.isAlert());
        assertEquals(time.isClockStarted(), timeResult.isClockStarted());
    }
}
