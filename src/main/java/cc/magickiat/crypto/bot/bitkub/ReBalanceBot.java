package cc.magickiat.crypto.bot.bitkub;

import cc.magickiat.crypto.bot.bitkub.service.BitKubService;
import cc.magickiat.crypto.bot.bitkub.dto.Balance;
import cc.magickiat.crypto.bot.bitkub.dto.Ticker;

import java.util.Map;

public class ReBalanceBot {
    public static void main(String[] args) throws Exception {
        BitKubService service = new BitKubService();

        Long serverTime = service.getServerTime();
        System.out.println("===== Server Time =====");
        System.out.println(serverTime);

        Map<String, Balance> balances = service.getBalances();
        System.out.println("===== My Balances =====");
        System.out.println(balances);

        Map<String, Ticker> tickerMap = service.getTickers();
        System.out.println("===== Tickers =====");
        System.out.println(tickerMap);

        tickerMap = service.getTicker("THB_BTC");
        System.out.println("===== Ticker for THB_BTC =====");
        System.out.println(tickerMap);
    }
}
