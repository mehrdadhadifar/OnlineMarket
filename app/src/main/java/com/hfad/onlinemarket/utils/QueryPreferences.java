package com.hfad.onlinemarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class QueryPreferences {
    public static final String PREF_KEY_CUSTOMER_EMAIL = "customerEmail";
    public static final String PREF_KEY_CUSTOMER_ID = "customerID";

    public static String getCustomerEmail(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(PREF_KEY_CUSTOMER_EMAIL, null);
    }

    public static void setCustomerEmail(Context context, String customerEmail) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putString(PREF_KEY_CUSTOMER_EMAIL, customerEmail).apply();
    }

    public static int getCustomerId(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getInt(PREF_KEY_CUSTOMER_ID, -1);
    }

    public static void setCustomerId(Context context, int customerId) {
        SharedPreferences preferences = getSharedPreferences(context);
        preferences.edit().putInt(PREF_KEY_CUSTOMER_ID, customerId).apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }


}
