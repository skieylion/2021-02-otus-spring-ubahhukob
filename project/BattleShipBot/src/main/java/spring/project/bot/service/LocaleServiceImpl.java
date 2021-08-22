package spring.project.bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import spring.project.bot.model.UserSettings;
import spring.project.bot.repository.UserSettingsRepository;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource messageSource;
    private final UserSettingsRepository userSettingsRepository;
    private final String localeDefault;

    public LocaleServiceImpl(MessageSource messageSource, UserSettingsRepository userSettingsRepository, @Value("${localeDefault}") String localeDefault) {
        this.messageSource = messageSource;
        this.userSettingsRepository = userSettingsRepository;
        this.localeDefault = localeDefault;
    }

    private String getLocale(Integer userId) {
        return userSettingsRepository.findById(userId).map(UserSettings::getLocale).orElse(localeDefault);
    }

    @Override
    public String localize(Integer userId, String text) {
        try {
            return messageSource.getMessage(text, null, Locale.forLanguageTag(getLocale(userId)));
        } catch (NoSuchMessageException exception) {
            return text;
        }
    }
}
