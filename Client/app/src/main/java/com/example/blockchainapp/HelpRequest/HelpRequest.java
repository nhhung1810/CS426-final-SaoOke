package com.example.blockchainapp.HelpRequest;

public class HelpRequest {
    private String campaignName;
    private String username;
    private Float amount;
    private String message;

    public HelpRequest(String campaignName, String username, Float amount, String message) {
        this.campaignName = campaignName;
        this.username = username;
        this.amount = amount;
        this.message = message;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
