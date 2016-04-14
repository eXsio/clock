package com.exsio.clock.controller;

import com.exsio.clock.util.LocaleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.exsio.jin.translator.Translator;

import java.util.Map;

@RestController
@RequestMapping("/i18n")
public class i18nController {

    private final Translator translator;

    @Autowired
    public i18nController(Translator translator) {
        this.translator = translator;
    }

    @RequestMapping("/{lang}")
    public Map<String, String> getTranslations(@PathVariable("lang") String lang) {
        return translator.getTranslations(LocaleValidator.validate(lang));
    }
}
