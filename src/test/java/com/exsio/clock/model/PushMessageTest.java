package com.exsio.clock.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PushMessageTest {

    private final static String TEST_OBJECT = "TEST_OBJECT";

    private PushMessage<String> underTest = new PushMessage<String>(TEST_OBJECT);


    @Test
    public void testGetters() {
        underTest.setObjectSerializationView(View.class);
        Assert.assertEquals(underTest.getObjectSerializationView(), View.class);
    }

    @Test
    public void testToString() {
        String string = underTest.toString();
        Assert.assertTrue(string.contains(TEST_OBJECT));
    }

    private interface View {

    }

}
