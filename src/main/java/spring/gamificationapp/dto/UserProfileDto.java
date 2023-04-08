package spring.gamificationapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class UserProfileDto {
    private String userName;
    private List<TaskDto> createdTasks;
    private BigDecimal tokens;
}
