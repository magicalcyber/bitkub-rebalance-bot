package cc.magickiat.crypto.bot.bitkub.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotConfig {
    private static final String ENABLE_BITKUB_SERVICE_LOG = "enable.bitkub.service.log";
    private static final String RE_BALANCE_ASSET_PAIR = "rebalance.asset.pair";
    private static final String RE_BALANCE_DIFF_PERCENT = "rebalance.diff.percent";

    private final Boolean enableBitKubServiceLog;
    private final String assetPair;
    private final double diffPercent;

    private static final BotConfig botConfig = new BotConfig();

    private BotConfig() {
        try (InputStream is = new FileInputStream("config/bot.properties")) {
            Properties prop = new Properties();
            prop.load(is);

            assetPair = prop.getProperty(RE_BALANCE_ASSET_PAIR);

            if (prop.containsKey(ENABLE_BITKUB_SERVICE_LOG)) {
                enableBitKubServiceLog = Boolean.valueOf(prop.getProperty(ENABLE_BITKUB_SERVICE_LOG));
            } else {
                enableBitKubServiceLog = false;
            }

            try {
                diffPercent = Double.parseDouble(prop.getProperty(RE_BALANCE_DIFF_PERCENT));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static BotConfig getInstance() {
        return botConfig;
    }

    public Boolean isEnableBitKubServiceLog() {
        return enableBitKubServiceLog;
    }

    public String getAssetPair() {
        return assetPair;
    }

    public double getDiffPercent() {
        return diffPercent;
    }
}
