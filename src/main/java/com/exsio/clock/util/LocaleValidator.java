package com.exsio.clock.util;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.exsio.jin.locale.provider.LocaleProvider;

import java.util.List;

public final class LocaleValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleValidator.class);
    public static final String DEFAULT_LOCALE = "en";
    public static final List<String> SUPPORTED_LOCALES = Lists.newArrayList(DEFAULT_LOCALE, "pl");

    private LocaleValidator() {
    }

    public static String validate(String requestedLocale) {
        String targetLocale = SUPPORTED_LOCALES.contains(requestedLocale) ? requestedLocale : DEFAULT_LOCALE;
        LOGGER.debug("Requested locale: {}, target locale: {}", requestedLocale, targetLocale);
        return targetLocale;
    }
}
