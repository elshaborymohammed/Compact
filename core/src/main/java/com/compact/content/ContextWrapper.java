package com.compact.content;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

/**
 * Created by lshabory on 10/23/2017.
 */

public class ContextWrapper extends android.content.ContextWrapper {

    public static Locale CURRENT = Locale.getDefault();

    private ContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context) {
        return wrap(context, CURRENT.getLanguage());
    }

    public static ContextWrapper wrap(Context context, String language) {
        Locale newLocale = new Locale(language);
        Locale.setDefault(newLocale);
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);

            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);

            context = context.createConfigurationContext(configuration);

        } else {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);
        }

        return new ContextWrapper(context);
    }
}
