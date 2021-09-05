package com.example.blockchainapp.Campaign;

import java.security.PublicKey;
import java.sql.Date;

public class Campaign {
    private PublicKey ownerKey;
    private String campaignName;
    private String ownerName;
    private String description;
    private Float targetAmount;
    private Date expireDate;
    private String propaganda;

    public Campaign(PublicKey ownerKey, String campaignName, String ownerName, String description, Float targetAmount, Date expireDate, String propaganda) {
        this.ownerKey = ownerKey;
        this.campaignName = campaignName;
        this.ownerName = ownerName;
        this.targetAmount = targetAmount;
        this.expireDate = expireDate;
        this.propaganda = propaganda;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Float getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Float targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getPropaganda() {
        return propaganda;
    }

    public void setPropaganda(String propaganda) {
        this.propaganda = propaganda;
    }
}
