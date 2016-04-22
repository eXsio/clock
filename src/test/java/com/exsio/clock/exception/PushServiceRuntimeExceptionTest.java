package com.exsio.clock.exception;

import com.exsio.clock.AbstractDisplayAwareTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PushServiceRuntimeExceptionTest extends AbstractDisplayAwareTest {

    private PushServiceRuntimeException underTest;

    private final static String TEST_MESSAGE = "TEST_MESSAGE";

    private final static Throwable TEST_THROWABLE = new Exception();

    @Test
    public void test1ArgConstructor() {
        underTest = new PushServiceRuntimeException(TEST_MESSAGE);
        Assert.assertEquals(underTest.getMessage(), TEST_MESSAGE);
        Assert.assertNull(underTest.getCause());
    }

    @Test
    public void test2ArgConstructor() {
        underTest = new PushServiceRuntimeException(TEST_MESSAGE, TEST_THROWABLE);
        Assert.assertEquals(underTest.getMessage(), TEST_MESSAGE);
        Assert.assertEquals(underTest.getCause(), TEST_THROWABLE);
    }
}
