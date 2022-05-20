package com.cosmas.moniger.utils;

public class Wallet {
    private int walletId;
    private String walletCurrency;
    private String walletName;
    private int image;
    private String walletImageName;

    public Wallet(String walletCurrency, String walletName, int image) {
        this.walletCurrency = walletCurrency;
        this.walletName = walletName;
        this.image = image;
        this.walletImageName = WalletImage.getImageName(image);
    }

    public Wallet(String walletCurrency, String walletName, String walletImageName)
    {
        this.walletCurrency = walletCurrency;
        this.walletName = walletName;
        this.walletImageName = walletImageName;
        this.image = WalletImage.getImage(walletImageName);
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public int getWalletId() {
        return walletId;
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

    public String getWalletImageName() {
        return walletImageName;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
