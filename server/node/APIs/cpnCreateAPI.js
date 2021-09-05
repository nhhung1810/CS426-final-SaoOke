const { Transaction } = require("../blockchain")

// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, description, propaganda)
// json send via body
// {
//     "ownerName" : "username", //=>auto get the existed public key
//     "campaignName" : "name",
//     "target" : int,
//     "expire" : "dd-mm-yyyy",
//     "msg" : "optional" //won't be include in the transaction
// }
module.exports = function (app, userFactory, campaignFactory, mCoin) {
    app.post("/cpncreate", function (req, res) {
        const check = (req) => {
            if (!req || !req.body) {
                console.log("Null Body! Check your params")
                return false;
            } else if (!req.body.ownerName || !req.body.campaignName
                || !req.body.targetAmount || !req.body.expireDate || !req.body.description || !req.body.propaganda) {
                console.log("Invalid Params! Check your params")
                return false;
            } else {
                try {
                    publicKey = userFactory.getKey(req.body.ownerName)
                    // console.log("\nCreate Campaign with:\n", req.body.ownerName, "\n", publicKey, "\nEnd Create\n")
                    // createCampaign(campaignName, ownerKey, ownerName, targetAmount, expireDate, message)
                    campaignFactory.createCampaign(req.body.campaignName, publicKey,
                        req.body.ownerName, req.body.targetAmount, req.body.expireDate, req.body.description, req.body.propaganda)
                    console.log("\n\nCheck campaign:\n", campaignFactory.getCampaignKey(req.body.campaignName), "\n\n")

                    return true
                } catch (error) {
                    console.log(error.toString())
                    return false;
                }
            }
        }
        
        var flag = null
        try{
            flag = check(req)
        } catch(err) {
            console.log(err.toString())
            return
        }

        if(flag === null) {
            res.send(404, { "error": "Unknow error" })
            return
        } else {
            if(!flag) {
                res.send(404, { "error": "Invalid params or error in getKey" })
                return
            } else {
                res.send(200, { "status": "success" })
                return
            }
        }
    })
}