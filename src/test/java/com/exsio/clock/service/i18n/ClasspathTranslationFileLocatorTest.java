package com.exsio.clock.service.i18n;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ClasspathTranslationFileLocatorTest {

    private final static String PATH = "classpath:test.txt";

    ClasspathTranslationFileLocator underTest;

    @Mock
    ApplicationContext context;

    @Mock
    Resource resource;

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(context.getResource(PATH)).thenReturn(resource);

        underTest = new ClasspathTranslationFileLocator(context);
    }

    @Test
    public void test_locateFile() throws IOException {
        underTest.locateFile(PATH);
        verify(context).getResource(PATH);
        verify(resource).getInputStream();

        verifyNoMoreInteractions(context);
        verifyNoMoreInteractions(resource);
    }
}
