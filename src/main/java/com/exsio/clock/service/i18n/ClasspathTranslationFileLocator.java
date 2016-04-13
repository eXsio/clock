package com.exsio.clock.service.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.exsio.jin.file.locator.TranslationFileLocator;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ClasspathTranslationFileLocator implements TranslationFileLocator {

    private final ApplicationContext context;

    @Autowired
    public ClasspathTranslationFileLocator(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public InputStream locateFile(String filePath) throws IOException {
        Resource resource = context.getResource(filePath);
        return resource.getInputStream();
    }
}
