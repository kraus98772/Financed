package com.cosmas.moniger;

public class Wallet {
    private Currency walletCurrency;
    private String walletName;
    private int image;

    public Wallet(Currency walletCurrency, String walletName, int image) {
        this.walletCurrency = walletCurrency;
        this.walletName = walletName;
        this.image = image;
    }

    public Currency getWalletCurrency() {
        return walletCurrency;
    }

    public void setWalletCurrency(Currency walletCurrency) {
        this.walletCurrency = walletCurrency;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
