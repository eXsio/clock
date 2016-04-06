package com.exsio.clock.service.push;

import com.exsio.clock.exception.PushServiceRuntimeException;
import com.exsio.clock.model.PushMessage;
import com.exsio.clock.service.push.MessageSerializer;
import com.exsio.clock.service.push.MessageSerializerImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonView;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class MessageSerializerTest {

    private final static String WITH_VIEW_1 = "WITH_VIEW_1";

    private final static String WITH_VIEW_2 = "WITH_VIEW_2";

    private MessageSerializer underTest = new MessageSerializerImpl();

    @Test
    public void testSerializeWithView1() throws IOException {
        PushMessage<TestObject> message = new PushMessage<TestObject>(new TestObject());
        message.setObjectSerializationView(View_1.class);

        String serializedMessage = underTest.serialize(message);
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_1));
        Assert.assertFalse(serializedMessage.contains(WITH_VIEW_2));
    }

    @Test
    public void testSerializeWithView2() throws IOException {
        PushMessage<TestObject> message = new PushMessage<TestObject>(new TestObject());
        message.setObjectSerializationView(View_2.class);

        String serializedMessage = underTest.serialize(message);
        Assert.assertFalse(serializedMessage.contains(WITH_VIEW_1));
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_2));
    }

    @Test
    public void testSerializeWithViewAll() throws IOException {
        PushMessage<TestObject> message = new PushMessage<TestObject>(new TestObject());
        message.setObjectSerializationView(View_all.class);

        String serializedMessage = underTest.serialize(message);
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_1));
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_2));
    }

    @Test
    public void testSerializeWithNoView() throws IOException {
        PushMessage<TestObject> message = new PushMessage<TestObject>(new TestObject());

        String serializedMessage = underTest.serialize(message);
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_1));
        Assert.assertTrue(serializedMessage.contains(WITH_VIEW_2));
    }

    @Test(expectedExceptions = PushServiceRuntimeException.class)
    public void testSerializeNull() {
        underTest.serialize(null);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,
            getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    public static class TestObject {

        @JsonView({View_1.class, View_all.class})
        private String withView1 = WITH_VIEW_1;

        @JsonView({View_2.class, View_all.class})
        private String withView2 = WITH_VIEW_2;


    }

    public interface View_all {

    }

    public interface View_1 {
    }

    public interface View_2 {
    }
}
