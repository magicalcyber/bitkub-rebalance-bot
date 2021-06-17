package cc.magickiat.crypto.bot.bitkub.dto;

import java.math.BigDecimal;

public class OrderResponse {
    private Long id;
    private String hash;
    private String typ;
    private BigDecimal amt;
    private BigDecimal rat;
    private BigDecimal fee;
    private BigDecimal cre;
    private BigDecimal rec;
    private Long ts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
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

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getCre() {
        return cre;
    }

    public void setCre(BigDecimal cre) {
        this.cre = cre;
    }

    public BigDecimal getRec() {
        return rec;
    }

    public void setRec(BigDecimal rec) {
        this.rec = rec;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", hash='" + hash + '\'' +
                ", typ='" + typ + '\'' +
                ", amt=" + amt +
                ", rat=" + rat +
                ", fee=" + fee +
                ", cre=" + cre +
                ", rec=" + rec +
                ", ts=" + ts +
                '}';
    }
}
