module.exports = function (app, mCoin) {
    // Route: /transactionLog
    // Method: GET
    // {
    //   "logs": [
    //       {
    //           "fromAddress": null,
    //           "toAddress": "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGOllTab/mVTMs3353mOBwjDp+\nM6LYYHi+ttH7/diA5PA7ZqJ2NtOzZXWjdaCGrqT/f0vkjWxCzhb1UOGZsSH+jVhK\niAsag+n2e+xzOPoe7xfWqOn3fI2Rt9yGswJcPP0mHUWsnlOuew9T+yyC7RFEFTX7\nRnD6gyYD8gbWvlFfuwIDAQAB\n-----END PUBLIC KEY-----",
    //           "amount": 100,
    //           "signature": "",
    //           "timestamp": 1630810142437
    //       }, ...
    //   ]
    // }
    app.get('/transactionsLog', function (req, res) {
        var transactions = mCoin.getTransactionsLog()
        res.send(200,
            {
                "logs": transactions
            }
        )
    })

    //inactive
    app.post('/mine', (req, res) => {
        console.log(req.body)
        if (!req || !req.body || !req.body.address) {
            res.send(404, {
                "status": "failed",
                "error": "Invalid request. Check your params"
            })
        } else {
            address = req.body.address
            mCoin.minePendingTransactions(address)
            res.send(200, {
                "status": "success"
            })
        }
    })
}