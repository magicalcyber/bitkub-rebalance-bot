package cc.magickiat.crypto.bot.bitkub.service;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiKeySecretInterceptor implements Interceptor {
    private static final String BITKUB_API_KEY = System.getenv("BITKUB_API_KEY");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        request = request.newBuilder()
                .removeHeader("x-btk-apikey")
                .addHeader("x-btk-apikey", BITKUB_API_KEY)
                .build();

        return chain.proceed(request);

    }
}