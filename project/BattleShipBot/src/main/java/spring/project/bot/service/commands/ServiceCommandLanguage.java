package spring.project.bot.service.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.project.bot.exception.EntityNotFoundException;
import spring.project.bot.model.ChatState;
import spring.project.bot.model.DataMessage;
import spring.project.bot.model.UserSettings;
import spring.project.bot.repository.ChatPlayerRepository;
import spring.project.bot.repository.UserSettingsRepository;
import spring.project.bot.service.TelegramService;

import java.util.Arrays;
import java.util.List;

@Service
public class ServiceCommandLanguage implements Command {

    private final List<ChatState> states = Arrays.asList(ChatState.CONFIG, ChatState.FREE, ChatState.PLAY, ChatState.WAIT);
    private final UserSettingsRepository userSettingsRepository;
    private final String localeDefault;

    public ServiceCommandLanguage(
            UserSettingsRepository userSettingsRepository,
            @Value("${localeDefault}") String localeDefault
    ) {
        this.userSettingsRepository = userSettingsRepository;
        this.localeDefault=localeDefault;
    }

    @Override
    public List<ChatState> getStates() {
        return states;
    }

    @Override
    public void execute(DataMessage data) {
        Integer userId = data.getUserId();
        String locale=data.getMessageText().replaceAll("/","");
        try {
            UserSettings userSettings = userSettingsRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
            userSettings.setLocale(locale);
            userSettingsRepository.save(userSettings);
        } catch (EntityNotFoundException exception) {
            UserSettings userSettings = new UserSettings();
            userSettings.setUserId(userId);
            userSettings.setLocale(localeDefault);
            userSettingsRepository.save(userSettings);
        }
    }
}
