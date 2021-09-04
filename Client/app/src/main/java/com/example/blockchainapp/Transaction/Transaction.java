package com.example.blockchainapp.Transaction;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction {

    private PublicKey fromAddress;
    private String toUser;
    private Float amount;
    private String message;

    public Transaction(PublicKey fromAddress, String toUser, Float amount, String message) {
        this.fromAddress = fromAddress;
        this.toUser = toUser;
        this.amount = amount;
        this.message = message;
    }

    public PublicKey getFromAddress() {
        return fromAddress;
    }

    public String getToUser() {
        return toUser;
    }

    public Float getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }

    public void setFromAddress(PublicKey fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
