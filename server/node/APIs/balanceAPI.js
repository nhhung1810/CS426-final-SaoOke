module.exports = function (app, userFactory, mCoin) {
  //param: address = username
  app.post("/balance/:username", function (req, res) {
    if (!req.params | !req.params.username) {
      res.send(404, { "error": "Invalid params" })
    }
    else {
      try {
        var publicKey = userFactory.getKey(req.params.username);
        // console.log("\nGet balance of:\n", publicKey, "\n")
        var tmp = mCoin.getBalanceOfAddress(publicKey)
        res.send(200, tmp )
      } catch (error) {
        console.log("\nGet balance: Exceptional. Check if user existed?\n")
        res.send(403, { "error": "Unable to find user" })
      }
    }
  })
}