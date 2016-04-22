package com.exsio.clock.service.clock;


import com.beust.jcommander.internal.Lists;
import com.exsio.clock.AbstractDisplayAwareTest;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.publisher.TimeInfoPublisher;
import com.google.common.base.Optional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static org.testng.AssertJUnit.assertTrue;

public class ClockServiceTest extends AbstractDisplayAwareTest {

    ClockService underTest;
    Optional<TimeInfo> time = Optional.absent();

    @BeforeMethod
    public void before() {
        time = Optional.absent();
        underTest = new ClockServiceImpl(Lists.<TimeInfoPublisher>newArrayList(new TestTimeInfoPublisher()));
    }

    @Test
    public void test_set() throws InterruptedException {
        underTest.set(0,1);
        assertTrue(time.isPresent());
        assertEquals(time.get().getTime(), "00:00");
        assertEquals(time.get().getBoundary(), "00:01");
        underTest.start();
        Thread.sleep(3000);
        underTest.stop();
        assertTrue(time.get().isAlert());
    }

    @Test
    public void test_reset() throws InterruptedException {
        underTest.start();
        Thread.sleep(2000);
        underTest.stop();
        Thread.sleep(2000);
        underTest.reset();
        assertTrue(time.isPresent());
        assertEquals(time.get().getTime(), "00:00");
    }

    @Test
    public void test_startStop() throws InterruptedException {
        underTest.set(0,0);
        underTest.start();
        Thread.sleep(2000);
        Assert.assertTrue(time.get().isClockStarted());
        Thread.sleep(2000);
        underTest.stop();
        Thread.sleep(2000);
        Assert.assertFalse(time.get().isClockStarted());
        assertTrue(time.isPresent());
        assertNotEquals(time.get().getTime(), "01:01");
        Assert.assertTrue(time.get().isAlert());
    }

    @Test
    public void test_getClockState() {
        underTest.set(0,1);
        TimeInfo timeInfo = underTest.getClockState();
        assertNotNull(timeInfo);
        assertFalse(timeInfo.isAlert());
        assertFalse(timeInfo.isClockStarted());
        assertEquals(timeInfo.getBoundary(),"00:01");
        assertEquals(timeInfo.getTime(), "00:00");
    }

    private class TestTimeInfoPublisher implements TimeInfoPublisher {

        @Override
        public void publish(TimeInfo timeInfo) {
            time = Optional.of(timeInfo);
        }
    }
}
