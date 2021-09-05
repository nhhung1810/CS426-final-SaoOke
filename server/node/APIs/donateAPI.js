const { Transaction } = require("../blockchain");

// POST: Donate (như transaction) (donatorKey, campaignName, amount) 
//=> Chuyển tiền bằng public key tới owner của cái campaign đó
module.exports = function (app, userFactory, campaignFactory, mCoin) {
    // Route: /donate
    // Method: POST
    // json sent via body
    // {
    //   "fromUser" : "admin",
    //   "campaignName" : "name",
    //   "amount" : 10,
    //   "signature" : """
    // }
    app.post('/donate', function (req, res) {
        const trans = (req) => {
            if (!req || !req.body) {
                console.log("How about null body");
                return false;
            }
            if (!req.body.fromUser || !req.body.campaignName || !req.body.amount || !req.body.signature) {
                console.log("Invalid transaction params")
                return false
            }
            else {
                try {
                    pubFrom = userFactory.getKey(req.body.fromUser);
                    pubCam = campaignFactory.getCampaignKey(req.body.campaignName)

                    if (pubCam != null && pubFrom != null) {

                        console.log("New transaction is been created")
                        return new Transaction(pubFrom,
                            pubCam,
                            req.body.amount,
                            req.body.signature)
                    } else {
                        console.log("Cannot find user or campaign")
                        return false
                    }
                } catch (ex) {
                    console.log(ex.toString())
                    return null
                }

            }
        }

        tx = trans(req)
        if (tx === null) {
            res.send(404, { "error": "Exception! Check your campaign name" })
            return
        } else {
            if (tx == false) {
                res.send(404, { "error": "Invalid params or information. Do you sure this username or campaign existed?" })
                return
            }

            try {
                if (!tx.isValid()) {
                    res.send(405, { "error": "Invalid signature" })
                    return;
                }
            } catch (err) {
                console.log(err)
                res.send(405, { "error": "Invalid public-key. Check both of your key" })
                return;
            }

            console.log("\nPassed the valid check!!\n")
            mCoin.addTransaction(tx)
            mCoin.minePendingTransactions("master-mine")

            if(campaignFactory.donate(req.body.campaignName, tx, "") == false){
                console.log("Campaign not found, please check why it able to reach this line???")
            }

            // console.log("\nCheck campaign\n", campaignFactory.getCampaignInformation(req.body.campaignName))
            res.send(200, { "status": "success" });
        }

    })
}