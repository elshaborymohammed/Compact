package com.compact.core.data;

import com.google.gson.Gson;

import java.util.Objects;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;
import dagger.multibindings.IntoSet;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@DisableInstallInCheck
public class RequestModule {

    @Provides
    @IntoSet
    Converter.Factory providesJsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @IntoSet
    CallAdapter.Factory providesCompactCallAdapterFactory() {
        return RxJava3CallAdapterFactory.createSynchronous();
    }

    @Provides
    @Singleton
    Retrofit.Builder providesRequestBuilder(Set<Converter.Factory> converterFactories,
                                            Set<CallAdapter.Factory> callAdapterFactories,
                                            @Named("url") String url) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Objects.requireNonNull(HttpUrl.parse(url)));

        converterFactories.forEach(builder::addConverterFactory);
        callAdapterFactories.forEach(builder::addCallAdapterFactory);

        return builder;
    }

    @Provides
    @Singleton
    Retrofit providesRequest(Retrofit.Builder builder, OkHttpClient.Builder okHttpClientBuilder) {
        return builder.client(okHttpClientBuilder.build()).build();
    }
}
