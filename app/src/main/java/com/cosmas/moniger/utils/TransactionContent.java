package com.cosmas.moniger.utils;

public class TransactionContent {
    private String category;
    private String description;

    public TransactionContent(String category, String description) {
        this.category = category;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
