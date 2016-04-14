package com.exsio.clock.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class LocaleValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(LocaleValidator.class);
    public final static String DEFAULT_LOCALE = "en";
    public final static List<String> SUPPORTED_LOCALES = Lists.newArrayList(DEFAULT_LOCALE, "pl");

    public static String validate(String requestedLocale) {
        String targetLocale = SUPPORTED_LOCALES.contains(requestedLocale) ? requestedLocale : DEFAULT_LOCALE;
        LOGGER.debug("Requested locale: {}, target locale: {}", requestedLocale, targetLocale);
        return targetLocale;
    }
}
