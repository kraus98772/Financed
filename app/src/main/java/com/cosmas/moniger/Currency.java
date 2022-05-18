package com.cosmas.moniger;

public class Currency {

    static final String USD = "USD";
    static final String USD_SYMBOL = "$";

    static final String GBP = "GBP";
    static final String GBP_SYMBOL = "₤";

    static final String EUR = "EUR";
    static final String EUR_SYMBOL = "€";

    static final String JPY = "JPY";
    static final String JPY_SYMBOL = "¥";

    static final String PLN = "PLN";
    static final String PLN_SYMBOL = "zl";

    public static String getCurrencySign(String currency)
    {
        if(currency.equals("EUR")) return "€";
        if(currency.equals("PLN")) return "zł";
        if(currency.equals("USD")) return "$";
        if(currency.equals("JPY")) return "¥";

        return "";
    }
}
