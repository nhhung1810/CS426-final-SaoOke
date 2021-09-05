const { Blockchain, Transaction } = require('./blockchain');

class Campaign {
    constructor(campaignName, ownerKey, ownerName, targetAmount, expireDate, description, propaganda = "") {
        this.campaignName = campaignName
        this.ownerKey = ownerKey
        this.ownerName = ownerName
        this.targetAmount = targetAmount
        this.expireDate = expireDate
        this.description = description
        this.propaganda = propaganda
        this.transactions = []
        this.requestHelpList = []
    }

    getReceivedAmount() {
        var amount = 0;
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
                    "amount": transaction.transaction.amount,
                    "message": transaction.message
                })
            } else {
                logs.push({
                    "amount": -transaction.transaction.amount,
                    "message": transaction.message
                })
            }
        });
        return logs
    }

    getAllDonators(userFactory) {
        var donators = []
        this.transactions.forEach(element => {
            // console.log("\n\n All donors: ", element, "\n\n")
            if (element.transaction.fromAddress != this.ownerKey) {
                // console.log("\nreaced here")
                donators.push(userFactory.getUsername(JSON.parse(JSON.stringify(element)).transaction.fromAddress))
            }
        });
        // console.log(donators)
        return donators
    }

    requestHelp(username, amount, message) {
        this.requestHelpList.push({
            "username": username,
            "amount": amount,
            "message": message
        })
    }

    getRequestHelpList() {
        return this.requestHelpList;
    }
}

class CampaignFactory {
    constructor() {
        this.campaignList = []
    }

    getCampaignByCampaignName(campaignName) {
        var campaign = null
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                campaign = element
            }
        });
        return campaign
    }

    getCampaignByOwner(username) {
        var result = []
        this.campaignList.forEach(element => {
            if (element.ownerName == username) {
                result.push({
                    "campaignName": element.campaignName,
                    "campaignOwnerKey": element.ownerKey,
                    "ownerName": element.ownerName,
                    "description": element.description,
                    "targetAmount": element.targetAmount,
                    "expireDate": element.expireDate,
                    "propaganda": element.propaganda,
                })
            }
        });
        return result
    }

    createCampaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, description, propaganda = "") {
        this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                throw new Error("This campaign name is exists")
            }
        });
        // console.log("\nCheck internal create Campaign\n", ownerKey);
        var tmp = new Campaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, description, propaganda)
        this.campaignList.push(tmp)
    }

    donate(campaignName, transaction, message) {
        var tmp = false;
        tmp = this.campaignList.forEach(element => {
            if (element.campaignName == campaignName) {
                // make new transaction here
                // console.log("REACHED HERE")
                element.transactions.push({
                    "transaction": transaction,
                    "message": message
                })
                return true;
            }
        });
        return tmp;
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
            "transaction": transaction,
            "message": message
        })
        return true
        //
    }

    getCampaignInformation(campaignName) {
        var result = this.getCampaignByCampaignName(campaignName)
        console.log(result)

        if (result == null) {
            return {
                "message": "Campaign does not exists"
            }
        }
        return {
            "campaignName": result.campaignName,
            "campaignOwnerKey": result.ownerKey,
            "ownerName": result.ownerName,
            "description": result.description,
            "targetAmount": result.targetAmount,
            "expireDate": result.expireDate,
            "propaganda": result.propaganda,
            "total_amount": result.getReceivedAmount()
        };
    }

    getCampaignKey(campaignName) {
        var key = null
        this.campaignList.forEach(element => {
            // console.log(element)
            if (element.campaignName == campaignName) {
                key = JSON.parse(JSON.stringify(element)).ownerKey;
            }
        });
        return key;
    }

    getCampaignHistory(campaignName) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        return campaign.getHistory()
    }

    getAllCampaign() {
        var result = []
        this.campaignList.forEach(campaign => {
            result.push({
                "campaignName": campaign.campaignName,
                "campaignOwnerKey": campaign.ownerKey,
                "expireDate": campaign.expireDate,
                "description": campaign.description,
                "targetAmount": campaign.targetAmount,
                "propaganda": campaign.propaganda,
                "totalAmount": campaign.getReceivedAmount()
            })
        });
        return result
    }

    getAllDonators(campaignName, userFactory) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign != null) {
            return JSON.parse(JSON.stringify(campaign.getAllDonators(userFactory)))
        }
        return null
    }

    requestHelp(campaignName, username, amount, message) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign == null) {
            throw new Error("campaign does not exists")
        }
        campaign.requestHelp(username, amount, message)
        return {
            "message": "Sent help successfully"
        }
    }

    getRequestHelpList(campaignName) {
        var campaign = this.getCampaignByCampaignName(campaignName)
        if (campaign == null) {
            throw new Error("Campaign does not exists")
        }
        return campaign.getRequestHelpList()
    }
}

module.exports.CampaignFactory = CampaignFactory
