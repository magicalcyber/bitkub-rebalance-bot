package cc.magickiat.crypto.bot.bitkub.service;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class HmacService {
    private static final String BITKUB_API_SECRET = System.getenv("BITKUB_API_SECRET");

    public static String calculateHmac(String data) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, BITKUB_API_SECRET).hmacHex(data);
    }
}
