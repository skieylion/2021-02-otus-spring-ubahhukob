package spring.project.bot.model;

import lombok.Data;

import java.util.List;

@Data
public class BattleFieldVO {
    private int sizeRow;
    private int sizeColumn;
    List<BattleCell> cells;
}
