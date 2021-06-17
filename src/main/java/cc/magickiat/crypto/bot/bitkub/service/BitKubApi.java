package cc.magickiat.crypto.bot.bitkub.service;


import cc.magickiat.crypto.bot.bitkub.dto.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;
import java.util.concurrent.Executor;

public interface BitKubApi {
    @GET("api/servertime")
    Call<Long> serverTime();

    @GET("api/market/ticker")
    Call<Map<String, Ticker>> marketTickers(@Query("sym") String sym);

    @POST("api/market/balances")
    @Headers({
            "accept: application/json",
            "content-type: application/json"
    })
    Call<BitKubResponseBody<Map<String, Balance>>> getBalances(@Body BitKubRequestBody body);

    @POST("api/market/place-bid/test")
    @Headers({
            "accept: application/json",
            "content-type: application/json"
    })
    Call<BitKubResponseBody<OrderResponse>> placeBidTest(@Body BidRequest body);

    @POST("api/market/place-bid")
    @Headers({
            "accept: application/json",
            "content-type: application/json"
    })
    Call<BitKubResponseBody<OrderResponse>> placeBid(@Body BidRequest body);

    @POST("api/market/place-ask/test")
    @Headers({
            "accept: application/json",
            "content-type: application/json"
    })
    Call<BitKubResponseBody<OrderResponse>> placeAskTest(@Body AskRequest req);

    @POST("api/market/place-ask")
    @Headers({
            "accept: application/json",
            "content-type: application/json"
    })
    Call<BitKubResponseBody<OrderResponse>> placeAsk(@Body AskRequest req);
}