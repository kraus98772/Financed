package com.cosmas.moniger;

public class WalletImage {

    static final Integer WALLET_IMAGE = R.drawable.ic_wallet_image;
    static final Integer DOLLAR_CASH_IMAGE = R.drawable.ic_dolar_cash_image;
    static final String WALLET_IMAGE_NAME = "WALLET_IMAGE";
    static final String DOLLAR_CASH_IMAGE_NAME = "DOLLAR_CASH_IMAGE";

    static Integer getImage(String name)
    {
        if(name.equals(WALLET_IMAGE_NAME))
        {
            return WALLET_IMAGE;
        }
        else if(name.equals(DOLLAR_CASH_IMAGE_NAME))
        {
            return DOLLAR_CASH_IMAGE;
        }
        return 0;
    }

    static String getImageName(int id)
    {
        if(id == WALLET_IMAGE)
        {
            return WALLET_IMAGE_NAME;
        }
        else if(id == DOLLAR_CASH_IMAGE)
        {
            return DOLLAR_CASH_IMAGE_NAME;
        }
        return "";
    }

}
