const { Transaction } = require("../blockchain");

module.exports = function (app, userFactory, mCoin) {
    // Route: /transaction
    // Method: POST
    // json sent via body
    // For example:
    // {
    //   "from" : "admin",
    //   "to" : "hung",
    //   "amount" : 10,
    //   "signature" : "ijA1JX6jnyd487Ba3ZsYCaan2XnuIqXkmx98HGpwFQkpzwzaZ3WskbJyFMGJkyogDYrnPq
    //        j+kCHL+qNJTEwE1gOsS3SWdG6+t78ce6eT0xFkJMS7N0Guu20ln9StCOio4pnKNz0ULH3epCn2VpfsDeS4/HcDJc4vKF2mUk1whM0="
    // }
    app.post('/transaction', function (req, res) {
        const trans = (req) => {
            if (!req || !req.body) {
                console.log("How about null body");
                return null;
            }
            if (!req.body.from || !req.body.to || !req.body.amount || !req.body.signature) {
                console.log("Invalid transaction params")
                return null
            }
            else {
                try {
                    publicKey = userFactory.getKey(req.body.from);
                    // console.log(publicKey)
                    console.log("New transaction is been created")
                    return new Transaction(publicKey,
                        req.body.to,
                        req.body.amount,
                        req.body.signature)
                } catch (ex) {
                    console.log(ex.toString())
                    return null
                }

            }
        }

        tx = trans(req)
        if (tx === null) {
            res.send(404, { "error": "Invalid params. Null key" })
        } else {
            // console.log(tx.signTransaction)
            try {
                if (!tx.isValid()) {
                    res.send(405, { "error": "Invalid signature" })
                    return;
                }
            } catch (err) {
                console.log(err)
                res.send(405, { "error": "Invalid sign key. Check your 'from' key" })
                return;
            }

            console.log("\nPassed the valid check!!\n")
            mCoin.addTransaction(tx)
            mCoin.minePendingTransactions("master-mine")
            res.send(200, { "status": "success" });
        }

    })
}