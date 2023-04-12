package spring.gamificationapp.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Component
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String optionText;

    public Option() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(id, option.id) && Objects.equals(optionText, option.optionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optionText);
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionText='" + optionText + '\'' +
                '}';
    }
}
