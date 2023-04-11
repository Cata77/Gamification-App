package spring.gamificationapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileDto {
    private String userName;
    private List<TaskDto> forbiddenTasks;
    private BigDecimal tokens;
    private int contributedTasks;
    private int correctSolvedTasks;
    private int wrongSolvedTasks;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<TaskDto> getForbiddenTasks() {
        return forbiddenTasks;
    }

    public void setForbiddenTasks(List<TaskDto> forbiddenTasks) {
        this.forbiddenTasks = forbiddenTasks;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal tokens) {
        this.tokens = tokens;
    }

    public int getContributedTasks() {
        return contributedTasks;
    }

    public void setContributedTasks(int contributedTasks) {
        this.contributedTasks = contributedTasks;
    }

    public int getCorrectSolvedTasks() {
        return correctSolvedTasks;
    }

    public void setCorrectSolvedTasks(int correctSolvedTasks) {
        this.correctSolvedTasks = correctSolvedTasks;
    }

    public int getWrongSolvedTasks() {
        return wrongSolvedTasks;
    }

    public void setWrongSolvedTasks(int wrongSolvedTasks) {
        this.wrongSolvedTasks = wrongSolvedTasks;
    }
}
