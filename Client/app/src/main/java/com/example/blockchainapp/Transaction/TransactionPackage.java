package com.example.blockchainapp.Transaction;

import org.json.JSONObject;

public class TransactionPackage {
    private Transaction transaction;
    private String signature;

    public TransactionPackage(Transaction transaction, String signature) {
        this.transaction = transaction;
        this.signature = signature;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
