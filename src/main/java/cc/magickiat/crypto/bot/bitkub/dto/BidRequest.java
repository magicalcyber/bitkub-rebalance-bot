package cc.magickiat.crypto.bot.bitkub.dto;

public class BidRequest extends Bid {
    private String sig;

    public BidRequest(Bid bid) {
        this.setAmt(bid.getAmt());
        this.setRat(bid.getRat());
        this.setTs(bid.getTs());
        this.setSym(bid.getSym());
        this.setTyp(bid.getTyp());
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }
}
