package cc.magickiat.crypto.bot.bitkub.dto;

public class BitKubResponseBody<T> {
    private int error;
    private T result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BitKubResponseBody{" +
                "error=" + error +
                ", result=" + result +
                '}';
    }
}
