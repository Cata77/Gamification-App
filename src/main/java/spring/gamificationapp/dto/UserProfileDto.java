package spring.gamificationapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileDto {
    private String userName;
    private List<TaskDto> forbiddenTasks;
    private BigDecimal tokens;

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
}
