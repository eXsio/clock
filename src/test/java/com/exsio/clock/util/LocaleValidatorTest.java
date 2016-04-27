package com.exsio.clock.util;

import com.exsio.clock.AbstractDisplayAwareTest;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

import static org.testng.Assert.assertEquals;

public class LocaleValidatorTest extends AbstractDisplayAwareTest {

    @Test
    public void test_construct() throws Exception {
        Constructor constructor = LocaleValidator.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void test_validate() {
        assertEquals(LocaleValidator.validate("pl"), "pl");
        assertEquals(LocaleValidator.validate("en"), "en");
        assertEquals(LocaleValidator.validate("de"), "en");

    }
}
