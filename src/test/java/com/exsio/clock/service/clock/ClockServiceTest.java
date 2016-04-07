package com.exsio.clock.service.clock;


import com.beust.jcommander.internal.Lists;
import com.exsio.clock.model.TimeInfo;
import com.exsio.clock.service.publisher.TimeInfoPublisher;
import com.google.common.base.Optional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertTrue;

public class ClockServiceTest {

    ClockService underTest = new ClockServiceImpl(Lists.<TimeInfoPublisher>newArrayList(new TestTimeInfoPublisher()));
    Optional<TimeInfo> time = Optional.absent();

    @BeforeMethod
    public void before() {
        time = Optional.absent();
    }

    @Test
    public void test_set() {
        underTest.set(1,1);
        assertTrue(time.isPresent());
        assertEquals(time.get().getTime(), " 01:01");
    }

    @Test
    public void test_reset() throws InterruptedException {
        underTest.set(1,1);
        underTest.start();
        Thread.sleep(2000);
        underTest.stop();
        underTest.reset();
        assertTrue(time.isPresent());
        assertEquals(time.get().getTime(), " 01:01");
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
        assertNotEquals(time.get().getTime(), " 01:01");
        Assert.assertTrue(time.get().isAlert());
    }

    private class TestTimeInfoPublisher implements TimeInfoPublisher {

        @Override
        public void publish(TimeInfo timeInfo) {
            time = Optional.of(timeInfo);
        }
    }
}