package com.exsio.clock.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.exsio.jin.ex.TranslationInitializationException;
import pl.exsio.jin.file.loader.TranslationFileLoader;
import pl.exsio.jin.file.loader.YamlTranslationFileLoaderImpl;
import pl.exsio.jin.file.locator.TranslationFileLocator;
import pl.exsio.jin.locale.provider.factory.LocaleProviderFactory;
import pl.exsio.jin.pluralizator.EnglishTranslationPluralizatorImpl;
import pl.exsio.jin.pluralizator.PolishTranslationPluralizatorImpl;
import pl.exsio.jin.pluralizator.TranslationPluralizator;
import pl.exsio.jin.pluralizator.registry.TranslationPluralizatorRegistry;
import pl.exsio.jin.pluralizator.registry.TranslationPluralizatorRegistryImpl;
import pl.exsio.jin.translationprefix.manager.TranslationPrefixManagerImpl;
import pl.exsio.jin.translator.Translator;
import pl.exsio.jin.translator.TranslatorImpl;

import java.util.HashMap;

@Configuration
public class i18nConfiguration {

    private final static Logger LOGGER = LoggerFactory.getLogger(i18nConfiguration.class);

    @Bean
    public Translator translator(TranslationFileLoader translationFileLoader, LocaleProviderFactory localeProviderFactory) {
        TranslatorImpl translator = new TranslatorImpl(translationFileLoader, localeProviderFactory);
        translator.setPrefixManager(new TranslationPrefixManagerImpl());
        TranslationPluralizatorRegistry pluralizators = new TranslationPluralizatorRegistryImpl();
        pluralizators.setPluralizators(new HashMap<String, TranslationPluralizator>() {
            {
                put("pl", new PolishTranslationPluralizatorImpl());
                put("en", new EnglishTranslationPluralizatorImpl());
            }
        });
        translator.setPluralizators(pluralizators);

        translator.setRegisterdFiles(new HashMap<String, String>() {
            {
                put("classpath:translations/pl.yml", "pl");
                put("classpath:translations/en.yml", "en");
            }
        });

        try {
            translator.init();
        } catch (TranslationInitializationException e) {
            LOGGER.error("{}", e.getMessage(), e);
        }
        return translator;
    }

    @Bean
    public TranslationFileLoader translationFileLoader(TranslationFileLocator locator) {
        return new YamlTranslationFileLoaderImpl(locator);
    }
}
