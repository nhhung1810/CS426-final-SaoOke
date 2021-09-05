const { Transaction } = require("../blockchain")

// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, description, propaganda)
// json send via body
// {
//     "ownerName" : "username", //=>auto get the existed public key
//     "campaignName" : "name",
//     "targetAmount" : int,
//     "expireDate" : "dd-mm-yyyy",
//     "description" : "long string",
//     "propaganda" : "optional" //won't be include in the transaction
// }
module.exports = function (app, userFactory, campaignFactory) {
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
                    if(publicKey != null)
                    {
                        campaignFactory.createCampaign(req.body.campaignName, publicKey,
                            req.body.ownerName, req.body.targetAmount, req.body.expireDate, req.body.description, req.body.propaganda)
                            return true
                    }
                    else {
                        console.log("Check for shallow copying of key");
                        return false;
                    }
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
            res.send(404, { "error": "Unknown error" })
            return
        } else {
            if(!flag) {
                res.send(404, { "error": "Invalid params or error in getKey" })
                return
            } else {
                console.log(campaignFactory.getAllCampaign())
                res.send(200, { "status": "success" })
                return
            }
        }
    })
}