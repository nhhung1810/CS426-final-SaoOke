package com.example.blockchainapp.Transaction;

import java.security.PublicKey;

public class GrantRequest {
    private String campaignName;
    private String receiver;
    private Long amount;
    private String message;
    private String signature;

    public GrantRequest(String campaignName, String receiver, Long amount, String message) {
        this.campaignName = campaignName;
        this.receiver = receiver;
        this.amount = amount;
        this.message = message;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
