package cc.magickiat.crypto.bot.bitkub;

import cc.magickiat.crypto.bot.bitkub.dto.Bid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

public class TestJson {
    public static void main(String[] args) throws JsonProcessingException {
        Bid bid = new Bid();
        bid.setSym("THB_BTC");
        bid.setAmt(new BigDecimal("0.00216507"));
        bid.setRat(new BigDecimal("1000"));
        bid.setTyp("market");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(bid));
    }
}
