package com.exsio.clock.service.i18n;


import com.exsio.clock.AbstractDisplayAwareTest;
import com.exsio.clock.util.LocaleValidator;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class SystemLocaleProviderFactoryTest extends AbstractDisplayAwareTest {

    private SystemLocaleProviderFactory underTest;

    @Test
    public void test_getLocaleProvider_default() {

        System.setProperty("user.language", LocaleValidator.DEFAULT_LOCALE);
        underTest = new SystemLocaleProviderFactory();

        assertNotNull(underTest.getLocaleProvider());
        assertEquals(underTest.getLocaleProvider().getLocale(), new Locale(LocaleValidator.DEFAULT_LOCALE));
        assertEquals(underTest.getLocaleProvider(), underTest.getLocaleProvider());
    }

    @Test
    public void test_getLocaleProvider_existing() {

        System.setProperty("user.language", LocaleValidator.SUPPORTED_LOCALES.get(1));
        underTest = new SystemLocaleProviderFactory();

        assertNotNull(underTest.getLocaleProvider());
        assertEquals(underTest.getLocaleProvider().getLocale(), new Locale(LocaleValidator.SUPPORTED_LOCALES.get(1)));
    }

    @Test
    public void test_getLocaleProvider_not_existing() {

        System.setProperty("user.language", "de");
        underTest = new SystemLocaleProviderFactory();

        assertNotNull(underTest.getLocaleProvider());
        assertEquals(underTest.getLocaleProvider().getLocale(), new Locale(LocaleValidator.DEFAULT_LOCALE));
    }
}
