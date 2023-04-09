package spring.gamificationapp.dto;

import java.math.BigDecimal;

public class UserTokensDto {
    private String userName;
    private BigDecimal tokens;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getTokens() {
        return tokens;
    }

    public void setTokens(BigDecimal tokens) {
        this.tokens = tokens;
    }
}
