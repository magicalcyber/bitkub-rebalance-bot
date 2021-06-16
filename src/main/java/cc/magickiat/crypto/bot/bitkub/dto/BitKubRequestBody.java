package cc.magickiat.crypto.bot.bitkub.dto;

public class BitKubRequestBody {
    private long ts;
    private String sig;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return "BitKubRequestBody{" +
                "ts=" + ts +
                ", sig='" + sig + '\'' +
                '}';
    }
}