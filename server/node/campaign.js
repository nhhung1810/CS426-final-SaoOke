const { Blockchain, Transaction } = require('./blockchain');

class Campaign {
    constructor(campaignName, ownerKey, ownerName, targetAmount, expireDate, message) {
        this.campaignName = campaignName
        this.ownerKey = ownerKey
        this.ownerName = ownerName
        this.targetAmount = targetAmount
        this.expireDate = expireDate
        this.message = message
        this.transactions = []
    }

    getReceivedAmount() {
        amount = 0;
        this.transactions.forEach(transaction => {
            if (this.ownerKey == transaction.transaction.toAddress) {
                amount += transaction.transaction.amount;
            } else {
                amount -= transaction.transaction.amount;
            }
        });
        return amount;
    }

    getHistory() {
        var logs = []
        this.transactions.forEach(transaction => {
            if (transaction.transaction.toAddress == this.ownerKey) {
                logs.push({
                    "amount" : transaction.transaction.amount,
                    "message" : transaction.message
                })
            } else {
                logs.push({
                    "amount" : -transaction.transaction.amount,
                    "message" : transaction.message
                })
            }
        });
    }
}

class CampaignFactory {
    constructor() {
        this.campaignList = []
    }

    createCampaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, message) {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                throw new Error("This campain name is exists")
            }
        });
        this.campaignList.push(new Campaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, message))
    }

    donate(donatorKey, campaignName, transaction, message) {
        var targetCampain = null
        this.campaignList.forEach(campaign => {
            if (campaign.campaignName == campaignName) {
                targetCampain = campaign
                break
            }
        });
        if (targetCampain == null) {
            throw new Error("Campaign not found");
        }

        // make new transaction here
        targetCampain.push({
            "transaction" : transaction,
            "message" : message
        })
        return true;
        //
    }

    give(campaignName, ownerKey, receiverKey, transaction, message) {
        var campaign = null
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                campaign = element
                break
            }
        });

        if (campaign == null) {
            throw new Error("campain not found!")
        }

        if (campaign.ownerKey != ownerKey) {
            throw new Error("campaign not match")
        }
    
        //make new transaction here
        campaign.push({
            "transaction" : transaction,
            "message" : message
        })
        return true
        //
    }

    getCampaignInformation(campaignName) {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                return {
                    "campaignName" : element.campaignName,
                    "ownerKey" : element.ownerKey,
                    "ownerName" : element.ownerName,
                    "targetAmount" : element.targetAmount,
                    "expireDate" : element.expireDate,
                    "total_amount" : element.getReceivedAmount()
                }
            }
        });
    }

    getCampaignHistory(campaignName) {
        this.campaignList.forEach(campaign => {
            if (Campaign.campaignName == campaignName) {
                return campaign.getHistory()
            }
        });
    }
}