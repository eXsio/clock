package com.exsio.clock.service.i18n;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.exsio.jin.locale.provider.DefaultLocaleProviderImpl;
import pl.exsio.jin.locale.provider.LocaleProvider;
import pl.exsio.jin.locale.provider.factory.LocaleProviderFactory;

import java.util.List;

@Service
public class SystemLocaleProviderFactory implements LocaleProviderFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(SystemLocaleProviderFactory.class);
    final static String DEFAULT_LOCALE = "en";
    final static List<String> LOCALES = Lists.newArrayList(DEFAULT_LOCALE, "pl");


    private final LocaleProvider localeProvider;

    public SystemLocaleProviderFactory() {
        String systemLocale = System.getProperty("user.language");
        String targetLocale = LOCALES.contains(systemLocale) ? systemLocale : DEFAULT_LOCALE;
        LOGGER.info("System locale: {}, target locale: {}", systemLocale, targetLocale);
        localeProvider = new DefaultLocaleProviderImpl(targetLocale);
    }

    @Override
    public LocaleProvider getLocaleProvider() {
        return localeProvider;
    }
}
