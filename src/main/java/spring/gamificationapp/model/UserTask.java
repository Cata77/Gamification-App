package spring.gamificationapp.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Entity
@Component
@Table(name = "user_task")
public class UserTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Task task;

    public UserTask() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTask userTask = (UserTask) o;
        return Objects.equals(id, userTask.id) && Objects.equals(user, userTask.user) && Objects.equals(task, userTask.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, task);
    }

    @Override
    public String toString() {
        return "UserTask{" +
                "id=" + id +
                ", user=" + user +
                ", task=" + task +
                '}';
    }
}