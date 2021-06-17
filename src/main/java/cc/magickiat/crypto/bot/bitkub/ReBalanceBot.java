package cc.magickiat.crypto.bot.bitkub;

import cc.magickiat.crypto.bot.bitkub.config.BotConfig;
import cc.magickiat.crypto.bot.bitkub.dto.Balance;
import cc.magickiat.crypto.bot.bitkub.service.BitKubService;
import cc.magickiat.crypto.bot.bitkub.service.ReBalanceListener;
import okhttp3.WebSocket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ReBalanceBot {
    private static final Logger LOGGER = LogManager.getLogger(ReBalanceBot.class.getName());

    public static void main(String[] args) throws Exception {
        BitKubService service = new BitKubService();

        Map<String, Balance> balances = service.getBalances();
        String assetPair = BotConfig.getInstance().getAssetPair();
        String[] assets = assetPair.split("_");
        String asset = assets[0];
        String coin = assets[1];

        Balance balanceAsset = balances.get(asset);
        if (balanceAsset == null) {
            LOGGER.warn("Not found balance for: {}", asset);
            return;
        }

        Balance balanceCoin = balances.get(coin);
        if (balanceCoin == null) {
            LOGGER.warn("Not found balance for: {}", coin);
            return;
        }

        LOGGER.info("Your asset: {}, balance: {}", asset, balanceAsset.getAvailable());
        LOGGER.info("Your coin: {}, balance: {}", coin, balanceCoin.getAvailable());

        ReBalanceListener listener = new ReBalanceListener(balanceAsset.getAvailable(), balanceCoin.getAvailable());
        WebSocket tradeStream = service.getTradeStream(listener);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> tradeStream.close(ReBalanceListener.NORMAL_CLOSURE_STATUS, "Bye")));
    }
}
