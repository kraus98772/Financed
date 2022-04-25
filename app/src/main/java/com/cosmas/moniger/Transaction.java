package com.cosmas.moniger;

import java.util.Date;

public class Transaction {
    private float transactionValue;
    private Date transactionDate;
    private String transactionCategory, transactionDescription;

    public Transaction(float transactionValue, Date transactionDate, String transactionCategory, String transactionDescription) {
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.transactionCategory = transactionCategory;
        this.transactionDescription = transactionDescription;
    }

    public float getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(float transactionValue) {
        this.transactionValue = transactionValue;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
}
