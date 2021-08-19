package spring.project.bot.model;

import lombok.Data;

@Data
public class DataMessage {
    private Long chatId;
    private Integer messageId;
    private String messageText;
    private String partnerId;
}
