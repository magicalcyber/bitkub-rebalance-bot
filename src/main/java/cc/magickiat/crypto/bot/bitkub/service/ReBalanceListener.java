package cc.magickiat.crypto.bot.bitkub.service;

import cc.magickiat.crypto.bot.bitkub.config.BotConfig;
import cc.magickiat.crypto.bot.bitkub.dto.OrderResponse;
import cc.magickiat.crypto.bot.bitkub.dto.Trade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ReBalanceListener extends WebSocketListener {
    private static final Logger LOGGER = LogManager.getLogger(ReBalanceListener.class.getName());

    private static final ObjectMapper mapper = new ObjectMapper();


    public static final int NORMAL_CLOSURE_STATUS = 1000;

    private BigDecimal latestPrice = BigDecimal.ZERO;

    private BigDecimal assetAmount;
    private BigDecimal coinAmount;
    private BitKubService callback;

    public ReBalanceListener(BigDecimal assetAmount, BigDecimal coinAmount, BitKubService service) {
        this.assetAmount = assetAmount;
        this.coinAmount = coinAmount;
        this.callback = service;

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            Trade trade = mapper.readValue(text, Trade.class);

            if (trade.getLastPrice().compareTo(latestPrice) == 0) {
                return;
            }

            latestPrice = trade.getLastPrice();

            LOGGER.debug("=====================");
            LOGGER.info("Latest price: " + latestPrice);
            LOGGER.debug("=====================");

            BigDecimal coinBalance = coinAmount.multiply(latestPrice);
            BigDecimal middlePrice = assetAmount.add(coinBalance)
                    .divide(new BigDecimal(2), RoundingMode.HALF_UP);

            showAmount(coinBalance, middlePrice);

            BigDecimal diff = coinBalance.subtract(assetAmount);
            BigDecimal diffAssetMiddle = assetAmount.subtract(middlePrice).abs();

            // diff over %
            BigDecimal diffPercent = diffAssetMiddle.multiply(new BigDecimal("100"))
                    .divide(assetAmount, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);

            LOGGER.info("\t> Diff {}%", diffPercent);
            if (isDiffPercentOverLimit(diffPercent)) {
                LOGGER.info("\t> Diff percent less than config: {}", BotConfig.getInstance().getDiffPercent());
                return;
            }

            if (diff.compareTo(BigDecimal.ZERO) < 0) {
                LOGGER.info("\t> Your asset balance {} is over coin balance {}", assetAmount.setScale(2, RoundingMode.HALF_UP), coinBalance.setScale(2, RoundingMode.HALF_UP));
                LOGGER.info("\t Should buy coin");

                BigDecimal totalAmountToBuy = diffAssetMiddle
                        .divide(latestPrice, 8, RoundingMode.HALF_UP);

                LOGGER.info("Total price to buy coin: {}", diffAssetMiddle.toPlainString());
                LOGGER.info("Total coin amount to buy: {}", totalAmountToBuy.toPlainString());

                // Test buy
                OrderResponse orderResponse = callback.placeTestBid(BotConfig.getInstance().getAssetPair(), diffAssetMiddle);
                LOGGER.info("Order Response: {}", orderResponse.toString());

                coinAmount = coinAmount.add(totalAmountToBuy);
                assetAmount = assetAmount.subtract(diffAssetMiddle);

                // re-cal coin price
                coinBalance = coinAmount.multiply(latestPrice);
                showAmount(coinBalance, middlePrice);
                return;
            }

            if (diff.compareTo(BigDecimal.ZERO) > 0) {
                LOGGER.info("\t> Your asset balance {} is less than coin balance {}", assetAmount.setScale(2, RoundingMode.HALF_UP), coinBalance.setScale(2, RoundingMode.HALF_UP));
                LOGGER.info("\t Should sell coin");

                // Find amount THB of coin to sell
                coinBalance = coinAmount.multiply(latestPrice);
                BigDecimal totalAmountToSell = coinBalance.subtract(middlePrice).divide(latestPrice, RoundingMode.HALF_UP);
                LOGGER.info("Total coin amount to sell: {}", totalAmountToSell);

                // Make sell
                coinAmount = coinAmount.subtract(totalAmountToSell);
                assetAmount = assetAmount.add(totalAmountToSell.multiply(latestPrice));

                // re-cal coin price
                coinBalance = coinAmount.multiply(latestPrice);
                showAmount(coinBalance, middlePrice);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDiffPercentOverLimit(BigDecimal diffPercent) {
        return diffPercent.compareTo(BigDecimal.valueOf(BotConfig.getInstance().getDiffPercent())) < 0;
    }

    private void showAmount(BigDecimal coinBalance, BigDecimal middlePrice) {
        LOGGER.debug("----------------------");
        LOGGER.debug("Middle price: {}", middlePrice.setScale(2, RoundingMode.HALF_UP));
        LOGGER.debug("Asset price: {}", assetAmount.setScale(2, RoundingMode.HALF_UP));
        LOGGER.debug("Coin price: {}", coinBalance.setScale(2, RoundingMode.HALF_UP));
        LOGGER.debug("----------------------");
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        LOGGER.info("Opening Stream...");
    }


    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        LOGGER.info("Closed Stream....");
    }
}
