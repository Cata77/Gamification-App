package spring.gamificationapp.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Component
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private List<Option> options;
    private String correctOption;
    private BigDecimal tokens;
    @Enumerated(EnumType.STRING)
    private Category category;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal tokens) {
        this.tokens = tokens;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description) && Objects.equals(options, task.options) && Objects.equals(correctOption, task.correctOption) && Objects.equals(tokens, task.tokens) && category == task.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, options, correctOption, tokens, category);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", options=" + options +
                ", correctOption='" + correctOption + '\'' +
                ", tokens=" + tokens +
                ", category=" + category +
                '}';
    }
}
