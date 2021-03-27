package spring.homework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ServiceLocaleImpl implements ServiceLocale {

    private final MessageSource messageSource;
    private final LocaleProvider localeProvider;

    public ServiceLocaleImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String localize(String marker, Object... args) {
        try {

            String message=messageSource.getMessage(marker, args, localeProvider.getLocale());
            //message=String.format(message,args);
            return message;
        } catch (NoSuchMessageException ex){
            return marker;
        }
    }
}
