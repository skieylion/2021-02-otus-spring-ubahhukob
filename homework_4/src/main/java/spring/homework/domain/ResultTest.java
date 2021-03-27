package spring.homework.domain;

import java.util.List;

public class ResultTest {
    private List<ItemSurvey> itemsSurvey;
    private int currentScore;
    private int maxScore;

    public List<ItemSurvey> getItemsSurvey() {
        return itemsSurvey;
    }

    public void setItemsSurvey(List<ItemSurvey> itemsSurvey) {
        this.itemsSurvey = itemsSurvey;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
}
