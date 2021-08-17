package spring.project.bot.model;

import lombok.Data;

@Data
public class MessageBotData {
    private Long chatId;
    private Integer messageId;
    private String messageText;
}
