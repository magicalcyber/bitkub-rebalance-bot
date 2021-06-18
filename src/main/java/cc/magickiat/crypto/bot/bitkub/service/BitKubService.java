package cc.magickiat.crypto.bot.bitkub.service;

import cc.magickiat.crypto.bot.bitkub.config.BotConfig;
import cc.magickiat.crypto.bot.bitkub.dto.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class BitKubService {

    private static final Logger LOGGER = LogManager.getLogger(BitKubService.class);

    private static final String BASE_URL = "https://api.bitkub.com";

    private static final String WEB_SOCKET_STREAM_TRADE = "/websocket-api/market.ticker.";

    private Retrofit createNormalRetrofit() {
        OkHttpClient.Builder clientBuilder = createHttpClientBuilder();
        return createRetrofit(clientBuilder);
    }

    private Retrofit createSecuredRetrofit() {
        OkHttpClient.Builder clientBuilder = createHttpClientBuilder();
        clientBuilder.addInterceptor(new ApiKeySecretInterceptor());
        return createRetrofit(clientBuilder);
    }

    private Retrofit createRetrofit(OkHttpClient.Builder clientBuilder) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(clientBuilder.build())
                .build();
    }

    private OkHttpClient.Builder createHttpClientBuilder() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BotConfig.getInstance().isEnableBitKubServiceLog()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }

        client.addInterceptor(new ErrorInterceptor());
        return client;
    }

    public WebSocket getTradeStream(ReBalanceListener listener) {
        OkHttpClient client = createHttpClientBuilder().build();

        Request request = new Request.Builder()
                .url(BASE_URL + WEB_SOCKET_STREAM_TRADE + BotConfig.getInstance().getAssetPair())
                .build();

        return client.newWebSocket(request, listener);
    }

    public Long getServerTime() throws IOException {
        Retrofit retrofit = createNormalRetrofit();
        BitKubApi api = retrofit.create(BitKubApi.class);
        return api.serverTime().execute().body();
    }

    public Map<String, Balance> getBalances() throws IOException {
        Retrofit securedRetrofit = createSecuredRetrofit();
        BitKubApi api = securedRetrofit.create(BitKubApi.class);

        Long ts = getServerTime();
        BitKubRequestBody requestBody = new BitKubRequestBody();
        requestBody.setTs(ts);
        requestBody.setSig(HmacService.calculateHmac("{\"ts\":" + ts + "}"));

        BitKubResponseBody<Map<String, Balance>> body = api.getBalances(requestBody).execute().body();
        if (body == null) {
            return Collections.emptyMap();
        }
        return body.getResult();
    }

    public OrderResponse placeTestBid(String symbol, BigDecimal amt) throws IOException {
        LOGGER.info(">>> Place BID test");
        Retrofit securedRetrofit = createSecuredRetrofit();
        BitKubApi api = securedRetrofit.create(BitKubApi.class);

        Long ts = getServerTime();

        Bid bid = new Bid();
        bid.setSym(symbol.toUpperCase());
        bid.setAmt(amt);
        bid.setRat(new BigDecimal("1000"));
        bid.setTyp("market");
        bid.setTs(ts);

        ObjectMapper mapper = new ObjectMapper();
        String sig = mapper.writeValueAsString(bid);
        LOGGER.debug("SIG = {}", sig);

        BidRequest req = new BidRequest(bid);
        req.setSig(HmacService.calculateHmac(sig));

        BitKubResponseBody<OrderResponse> body = api.placeBidTest(req).execute().body();
        if (body == null) {
            return null;
        }
        return body.getResult();
    }

    public OrderResponse placeBid(String symbol, BigDecimal amt) throws IOException {
        LOGGER.info(">>> Place real BID");
        Retrofit securedRetrofit = createSecuredRetrofit();
        BitKubApi api = securedRetrofit.create(BitKubApi.class);

        Long ts = getServerTime();

        Bid bid = new Bid();
        bid.setSym(symbol.toUpperCase());
        bid.setAmt(amt);
        bid.setRat(new BigDecimal("1000"));
        bid.setTyp("market");
        bid.setTs(ts);

        ObjectMapper mapper = new ObjectMapper();
        String sig = mapper.writeValueAsString(bid);
        LOGGER.debug("SIG = {}", sig);

        BidRequest req = new BidRequest(bid);
        req.setSig(HmacService.calculateHmac(sig));

        BitKubResponseBody<OrderResponse> body = api.placeBid(req).execute().body();
        if (body == null) {
            return null;
        }
        return body.getResult();
    }

    public OrderResponse placeTestAsk(String symbol, BigDecimal amt) throws IOException {
        LOGGER.info(">>> Place BID test");
        Retrofit securedRetrofit = createSecuredRetrofit();
        BitKubApi api = securedRetrofit.create(BitKubApi.class);

        Long ts = getServerTime();

        Ask ask = new Ask();
        ask.setSym(symbol.toUpperCase());
        ask.setAmt(amt);
        ask.setRat(new BigDecimal("1000"));
        ask.setTyp("market");
        ask.setTs(ts);

        ObjectMapper mapper = new ObjectMapper();
        String sig = mapper.writeValueAsString(ask);
        LOGGER.debug("SIG = {}", sig);

        AskRequest req = new AskRequest(ask);
        req.setSig(HmacService.calculateHmac(sig));

        BitKubResponseBody<OrderResponse> body = api.placeAskTest(req).execute().body();
        if (body == null) {
            return null;
        }
        return body.getResult();
    }

    public OrderResponse placeAsk(String symbol, BigDecimal amt) throws IOException {
        LOGGER.info(">>> Place BID test");
        Retrofit securedRetrofit = createSecuredRetrofit();
        BitKubApi api = securedRetrofit.create(BitKubApi.class);

        Long ts = getServerTime();

        Ask ask = new Ask();
        ask.setSym(symbol.toUpperCase());
        ask.setAmt(amt);
        ask.setRat(new BigDecimal("1000"));
        ask.setTyp("market");
        ask.setTs(ts);

        ObjectMapper mapper = new ObjectMapper();
        String sig = mapper.writeValueAsString(ask);
        LOGGER.debug("SIG = {}", sig);

        AskRequest req = new AskRequest(ask);
        req.setSig(HmacService.calculateHmac(sig));

        BitKubResponseBody<OrderResponse> body = api.placeAsk(req).execute().body();
        if (body == null) {
            return null;
        }
        return body.getResult();
    }
    public Map<String, Ticker> getTickers() throws IOException {
        Retrofit retrofit = createNormalRetrofit();
        BitKubApi api = retrofit.create(BitKubApi.class);
        return api.marketTickers(null).execute().body();
    }

    public Map<String, Ticker> getTicker(String symbol) throws IOException {
        Retrofit retrofit = createNormalRetrofit();
        BitKubApi api = retrofit.create(BitKubApi.class);
        return api.marketTickers(symbol).execute().body();
    }
}
