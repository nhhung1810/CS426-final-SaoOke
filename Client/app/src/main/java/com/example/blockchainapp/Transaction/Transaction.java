package com.example.blockchainapp.Transaction;

public class Transaction {

    private String fromAddress;
    private String toAddress;
    private Float amount;
    private String message;

    public Transaction(String fromAddress, String toAddress, Float amount, String message) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.message = message;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public Float getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
