package com.exsio.clock.controller;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.exsio.jin.translator.Translator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class I18nControllerTest {

    private final static String LANG = "EN";

    i18nController underTest;

    @Mock
    Translator translator;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        underTest = new i18nController(translator);
    }

    @Test
    public void test_getTranslations() {
        underTest.getTranslations(LANG);

        verify(translator).getTranslations(LANG);
        verifyNoMoreInteractions(translator);
    }
}
