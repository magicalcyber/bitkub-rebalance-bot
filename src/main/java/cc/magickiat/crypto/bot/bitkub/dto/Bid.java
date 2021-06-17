package cc.magickiat.crypto.bot.bitkub.dto;

import java.math.BigDecimal;

public class Bid {
    private String sym;
    private BigDecimal amt;
    private BigDecimal rat;
    private String typ;
    private long ts;

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getRat() {
        return rat;
    }

    public void setRat(BigDecimal rat) {
        this.rat = rat;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
