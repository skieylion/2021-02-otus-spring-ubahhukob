package spring.homework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleProviderImpl implements LocaleProvider {
    private Locale locale;

    public LocaleProviderImpl(@Value("${languageTag}") String languageTag) {
        this.locale = Locale.forLanguageTag(languageTag);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }
}
