package spring.gamificationapp.dto;

import java.math.BigDecimal;
import java.util.List;

public class CategoryTaskDto {
    private String description;
    private List<OptionDto> options;
    private BigDecimal tokens;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal tokens) {
        this.tokens = tokens;
    }
}
