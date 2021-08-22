package spring.project.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DataMessage {
    private Long chatId;
    private Integer messageId;
    private String messageText;
    private String partnerId;
    private Integer userId;
}
