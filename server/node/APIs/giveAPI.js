const { Transaction } = require("../blockchain");

// POST: Give (như transaction) (campaignOwner, receiver, amount , signature, message)
module.exports = function (app, userFactory, campaignFactory, mCoin) {
    // Route: /give
    // Method: POST
    // json sent via body
    // {
    //   "campaignOwner" : "", => auto fetch
    //   "receiver" : "",
    //   "amount" : 10,
    //   "signature" : "",
    //   "message" : ""
    // }
    app.post('/donate', function (req, res) {
        const trans = (req) => {
            if (!req || !req.body) {
                console.log("How about null body");
                return false;
            }
            if (!req.body.campaignOwner || !req.body.receiver || !req.body.amount || !req.body.signature || !req.body.message) {
                console.log("Invalid transaction params")
                return false;
            }
            try {
                pubOwn = userFactory.getKey(!req.body.campaignOwner)
                pubRe = userFactory.getKey(!req.body.receiver)
                if (pubOwn != null && pubRe != null) {
                    return new Transaction(pubOwn, pubRe, req.body.amount, req.signature)
                } else {
                    console.log("Check your copying again")
                    return false;
                }
            } catch (error) {
                console.log(error.toString())
                return null
            }
        }

        var tx = trans(req)
        if (tx == null) {
            res.send(404, { "error": "Exception! Check all the name" })
            return
        }
        if (tx == false) {
            res.send(404, { "error": "Invalid Params or Key" })
            return
        }

        try {
            if (!tx.isValid()) {
                res.send(403, { "error": "Invalid signature" })
                return;
            }
        } catch (err) {
            console.log(err.toString())
            res.send(403, { "error": "Exception! Check your keys" })
            return;
        }

        console.log("\nPassed the valid check!!\n")
        mCoin.addTransaction(tx)
        mCoin.minePendingTransactions("master-mine")
        res.send(200, { "status": "success" });
    })
}