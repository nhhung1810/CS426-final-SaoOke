module.exports = function (app) {
  //param: address = username
  app.post("/balance/:username", function (req, res) {
    if (!req.params | !req.params.username) {
      res.send(404, { "error": "Invalid params" })
    }
    else {
      //remerber fix this pls, this should be accept an public key
      try {
        console.log("Exception! Check if user existed?")
        var publicKey = userFactory.getKey(req.params.username);
        console.log("\nGet balance of:\n", publicKey, "\n")
        var tmp = mCoin.getBalanceOfAddress(publicKey)
        res.send(200, { "balance": tmp })
      } catch (error) {
        res.send(403, { "error": "Unable to find user" })
      }
    }
  })
}