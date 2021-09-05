package com.example.blockchainapp.HelpRequest;

import java.security.PublicKey;

public class HelpRequest {
    private PublicKey requesterKey;
    private Float amount;
    private String message;

    public HelpRequest(PublicKey requesterKey, Float amount, String message) {
        this.requesterKey = requesterKey;
        this.amount = amount;
        this.message = message;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
