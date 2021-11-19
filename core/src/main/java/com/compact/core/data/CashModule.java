package com.compact.core.data;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.migration.DisableInstallInCheck;
import okhttp3.Cache;

@Module
@DisableInstallInCheck
public class CashModule {

    @Provides
    @Singleton
    Cache provideOkHttpCache(@ApplicationContext Context context) {
        int cacheSize = 50 * 1024 * 1024; // 50 MiB
        return new Cache(context.getCacheDir(), cacheSize);
    }
}
