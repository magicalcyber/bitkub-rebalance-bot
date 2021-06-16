package cc.magickiat.crypto.bot.bitkub.service;

import cc.magickiat.crypto.bot.bitkub.dto.Balance;
import cc.magickiat.crypto.bot.bitkub.dto.BitKubRequestBody;
import cc.magickiat.crypto.bot.bitkub.dto.Ticker;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Map;

public class BitKubService {

    private static final String BASE_URL = "https://api.bitkub.com";

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
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.addInterceptor(logging);
        client.addInterceptor(new ErrorInterceptor());
        return client;
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
        return api.getBalances(requestBody).execute().body().getResult();
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
