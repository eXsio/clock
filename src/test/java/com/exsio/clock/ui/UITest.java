package com.exsio.clock.ui;


import com.exsio.clock.AbstractDisplayAwareTest;
import com.google.common.base.Optional;
import org.testng.annotations.Test;

import java.awt.*;
import java.lang.reflect.Constructor;

import static org.testng.Assert.assertTrue;

public class UITest extends AbstractDisplayAwareTest {

    @Test
    public void test_getDesktop() {
        Optional<Desktop> result = UI.getDesktop();
        assertTrue(result.isPresent());
    }

    @Test
    public void test_construct() throws Exception {
        Constructor constructor = UI.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
