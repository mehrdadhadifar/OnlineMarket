package com.hfad.onlinemarket.utils;

public class PriceFormatter {
    public static String priceFormatter(String price) {
        String result = "";
        while (price.length() > 3) {
            result = "," + price.substring(price.length() - 3).concat(result);
            price = price.substring(0, price.length() - 3);
        }
        result = price.concat(result);
        return result;
    }
}
