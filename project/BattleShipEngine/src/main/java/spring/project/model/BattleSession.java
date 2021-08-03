package spring.project.model;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Random;

@Data
public class BattleSession {
    private String id;
    private HashMap<String, BattleField> battleField;

    public BattleSession() {
        id = RandomStringUtils.random(8, "0123456789ABCDEF");
        battleField = new HashMap<>();
    }

    public void setBattleField(String playerName, BattleField field) {
        battleField.put(playerName,field);
    }
    public BattleField getBattleFieldByPlayerName(String name){
        return battleField.get(name);
    }
}
