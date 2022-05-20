package com.cosmas.moniger.utils;

public class Currency {

    public static final String USD = "USD";
    public static final String USD_SYMBOL = "$";

    public static final String GBP = "GBP";
    public static final String GBP_SYMBOL = "₤";

    public static final String EUR = "EUR";
    public static final String EUR_SYMBOL = "€";

    public static final String JPY = "JPY";
    public static final String JPY_SYMBOL = "¥";

    public static final String PLN = "PLN";
    public static final String PLN_SYMBOL = "zl";

    public static String getCurrencySign(String currency)
    {
        if(currency.equals("EUR")) return "€";
        if(currency.equals("PLN")) return "zł";
        if(currency.equals("USD")) return "$";
        if(currency.equals("JPY")) return "¥";

        return "";
    }
}
