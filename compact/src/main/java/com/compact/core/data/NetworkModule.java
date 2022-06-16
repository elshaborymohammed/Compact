package com.compact.core.data;

import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;
import dagger.multibindings.IntoSet;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
@DisableInstallInCheck
public class NetworkModule {

    @Provides
    @IntoSet
    Interceptor providesBodyInterceptors() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    OkHttpClient.Builder providesOkHttpClient(Cache cache, Set<Interceptor> interceptorSet, Authenticator authenticator) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
//                .callTimeout(3L, TimeUnit.SECONDS)
//                .readTimeout(30L, TimeUnit.SECONDS)
//                .writeTimeout(60L, TimeUnit.SECONDS)
                ;

        builder.interceptors().addAll(interceptorSet);
        builder.authenticator(authenticator);
        return builder;
    }
}
