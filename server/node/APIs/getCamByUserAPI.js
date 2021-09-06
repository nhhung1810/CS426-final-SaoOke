module.exports = function (app, campaignFactory) {
    // Route: /getCamByUser/:campaignName
    // Method: GET
    app.get("/getCamByUser/:username", function(req, res){
        if (!req.params || !req.params.username) {
            res.send(404, "Invalid param")
        }
        var tmp = null
        try {
            console.log("\nCheck username:", req.params.username)
            var tmp = campaignFactory.getCampaignByOwner(req.params.username)
            if(tmp == null){
                res.send(404, {"error" : "Invalid username"})
                return
            }
        } catch (err) {
            console.log("\n", err.toString(), "\n")
            res.send(404, "Error")
            return
        }
        res.send(200, tmp)
    })
}