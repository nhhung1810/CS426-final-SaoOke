package com.example.blockchainapp.Transaction;

import java.security.PublicKey;

public class DonationRequest {
    private String campaignName;
    private String fromUser;
    private Long amount;
    private String message;
    private String signature;

    public DonationRequest(String campaignName, String fromUser, Long amount, String message) {
        this.campaignName = campaignName;
        this.fromUser = fromUser;
        this.amount = amount;
        this.message = message;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
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
