package com.exsio.clock.service.i18n;

import com.exsio.clock.util.LocaleValidator;
import org.springframework.stereotype.Service;
import pl.exsio.jin.locale.provider.DefaultLocaleProviderImpl;
import pl.exsio.jin.locale.provider.LocaleProvider;
import pl.exsio.jin.locale.provider.factory.LocaleProviderFactory;

@Service
public class SystemLocaleProviderFactory implements LocaleProviderFactory {

    private final LocaleProvider localeProvider;

    public SystemLocaleProviderFactory() {
        localeProvider = new DefaultLocaleProviderImpl(
                LocaleValidator.validate(System.getProperty("user.language"))
        );
    }

    @Override
    public LocaleProvider getLocaleProvider() {
        return localeProvider;
    }
}
