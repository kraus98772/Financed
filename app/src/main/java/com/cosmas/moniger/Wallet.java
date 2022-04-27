package com.cosmas.moniger;

public class Wallet {
    private String walletCurrency;
    private String walletName;
    private int image;

    public Wallet(String walletCurrency, String walletName, int image) {
        this.walletCurrency = walletCurrency;
        this.walletName = walletName;
        this.image = image;
    }

    public String getWalletCurrency() {
        return walletCurrency;
    }

    public void setWalletCurrency(String walletCurrency) {
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
