package cc.magickiat.crypto.bot.bitkub.dto;

import java.math.BigDecimal;

public class Ticker {
    private int id;
    private BigDecimal last;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "id=" + id +
                ", last=" + last +
                '}';
    }
}