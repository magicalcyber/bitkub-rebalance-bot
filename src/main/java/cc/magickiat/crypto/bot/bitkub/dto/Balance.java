package cc.magickiat.crypto.bot.bitkub.dto;

import java.math.BigDecimal;

public class Balance {
    private BigDecimal available;
    private BigDecimal reserved;

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public BigDecimal getReserved() {
        return reserved;
    }

    public void setReserved(BigDecimal reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "available=" + available +
                ", reserved=" + reserved +
                '}';
    }
}