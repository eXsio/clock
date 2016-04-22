package com.exsio.clock;

import org.testng.annotations.BeforeSuite;

public class AbstractDisplayAwareTest {

    @BeforeSuite
    protected void suiteSetup()
    {
        System.setProperty("java.awt.headless", "false");
    }
}
