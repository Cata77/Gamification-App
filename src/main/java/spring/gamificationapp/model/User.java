package spring.gamificationapp.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Component
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<UserTask> forbiddenTasks;
    private BigDecimal tokens;

    public User() {
        this.tokens = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal score) {
        this.tokens = score;
    }

    public List<UserTask> getForbiddenTasks() {
        return forbiddenTasks;
    }

    public void setForbiddenTasks(List<UserTask> forbiddenTasks) {
        this.forbiddenTasks = forbiddenTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(forbiddenTasks, user.forbiddenTasks) && Objects.equals(tokens, user.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, forbiddenTasks, tokens);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", forbiddenTasks=" + forbiddenTasks +
                ", tokens=" + tokens +
                '}';
    }
}
