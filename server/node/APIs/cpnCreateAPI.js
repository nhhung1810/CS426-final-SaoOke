
// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, message)
// json send vis body
// {
//     "ownerName" : "username", //=>auto get the existed public key
//     "campaignName" : "name",
//     "target" : "int",
//     "expire" : "dd-mm-yyyy",
//     "msg" : "optional" //won't be include in the transaction
// }
module.exports = function (app, userFactory, mCoin) {
    app.post("./cpncreate", function (req, res) {
        const check = (req) => {
            if (!req || !req.body) {
                console.log("Null Body! Check your params")
                return null
            } else if (!req.body.ownerName || !req.body.campaignName
                || !req.body.target || !req.body.expire || !req.body.msg) {
                    console.log("Invalid Params! Check your params")
                    return null
            } else {
                publicKey = userFactory.getKey(req.body.ownerName)
            }
        }
    })
}