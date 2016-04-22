package com.exsio.clock.controller;

import com.exsio.clock.annotation.JsonpController;
import com.exsio.clock.util.LocaleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.exsio.jin.translator.Translator;

import java.util.Map;

@JsonpController
@RequestMapping("/i18n")
public class i18nController {

    private final Translator translator;

    @Autowired
    public i18nController(Translator translator) {
        this.translator = translator;
    }

    @RequestMapping(value = "/{lang}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getTranslations(@PathVariable("lang") String lang) {
        return translator.getTranslations(LocaleValidator.validate(lang));
    }
}
