package spring.project.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleField {
    private List<List<String>> field;
    private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private static String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public void fire(String target) {
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if (target.equals(letters[i] + numbers[j]) || target.equals(numbers[j] + letters[i])) {

                }
            }
        }
    }

}
