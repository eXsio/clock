package com.exsio.clock.configuration;


import com.exsio.clock.AbstractDisplayAwareTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.exsio.jin.file.loader.TranslationFileLoader;
import pl.exsio.jin.file.locator.TranslationFileLocator;
import pl.exsio.jin.locale.provider.factory.LocaleProviderFactory;
import pl.exsio.jin.translationcontext.TranslationContext;
import pl.exsio.jin.translator.Translator;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class i18nConfigurationTest extends AbstractDisplayAwareTest {

    private i18nConfiguration underTest = new i18nConfiguration();

    @Mock
    private TranslationFileLoader translationFileLoader;

    @Mock
    private LocaleProviderFactory localeProviderFactory;

    @Mock
    private TranslationFileLocator translationFileLocator;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_translator() {

        Translator result = underTest.translator(translationFileLoader, localeProviderFactory);
        assertNotNull(result);
        assertTrue(result.isInitialized());
        TranslationContext.setTranslator(null);
    }

    @Test
    public void test_translationFileLoader() {
        TranslationFileLoader result = underTest.translationFileLoader(translationFileLocator);
        assertNotNull(result);
    }
}
