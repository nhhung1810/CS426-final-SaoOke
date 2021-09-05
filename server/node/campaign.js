
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
            if (this.ownerKey == transaction.toAddress) {
                amount += transaction.amount;
            } else {
                amount -= transaction.amount;
            }
        });
        return amount;
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

    donate(donatorKey, campaignName, amount, message) {
        targetCampain = null
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
        
        //
    }

    give(ownerKey, receiverKey, amount, message) {
        //make new transaction here
        return null
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
                    "total_amount" : element.getReceivedAmount
                }
            }
        });
    }
}

module.exports.CampaignFactory = CampaignFactory