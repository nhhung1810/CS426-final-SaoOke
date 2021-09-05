
// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, message)
// json send vis body
// {
//     "ownerName" : "username", //=>auto get the existed public key
//     "campaignName" : "name",
//     "target" : "int",
//     "expire" : "dd-mm-yyyy",
//     "msg" : "optional" //won't be include in the transaction
// }
module.exports = function(app){
    app.post("./cpncreate", function(req, res){
        console.log(404)
    })
}