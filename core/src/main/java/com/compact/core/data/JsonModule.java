package com.compact.core.data;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;

@Module
@DisableInstallInCheck
public class JsonModule {
    @Provides
    @Singleton
    Gson providesJson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat(providesDatePattern())
                .create();
    }

    public static String providesDatePattern() {
        return "yyyy-MM-dd'T'HH:mm:ssZ";
    }
}
