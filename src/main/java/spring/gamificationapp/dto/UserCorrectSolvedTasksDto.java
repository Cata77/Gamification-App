package spring.gamificationapp.dto;

public class UserCorrectSolvedTasksDto {
    private String userName;
    int correctSolvedTasks;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCorrectSolvedTasks() {
        return correctSolvedTasks;
    }

    public void setCorrectSolvedTasks(int correctSolvedTasks) {
        this.correctSolvedTasks = correctSolvedTasks;
    }
}
