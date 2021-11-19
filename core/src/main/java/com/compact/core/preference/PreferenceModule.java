package com.compact.core.preference;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.migration.DisableInstallInCheck;

@Module
@DisableInstallInCheck
public class PreferenceModule {

    @Singleton
    @Provides
    SharedPreferences providesDataStore(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
