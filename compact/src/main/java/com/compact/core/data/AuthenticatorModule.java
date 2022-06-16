package com.compact.core.data;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.migration.DisableInstallInCheck;
import okhttp3.Authenticator;

@Module
@DisableInstallInCheck
public class AuthenticatorModule {

    @Provides
    @Singleton
    Authenticator authenticator() {
        return Authenticator.NONE;
    }
}
