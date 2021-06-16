package cc.magickiat.crypto.bot.bitkub.service;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class ErrorInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        boolean httpError = (response.code() >= 400);
        if (httpError) {
            throw new HttpException(response.body().string());
        }

        return response;
    }
}
