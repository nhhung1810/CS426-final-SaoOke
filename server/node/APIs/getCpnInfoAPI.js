// GET: Get Campaign Info (campaignName)
module.exports = function (app, campaignFactory) {
    app.get("/campaign/:campaignName", function (req, res) {
        if (!req.params || !req.params.campaignName) {
            res.send(404, "Invalid campaign name")
        }        
        try {
            res.send(200, campaignFactory.getCampaignInformation(req.params.campaignName))      
        } catch (err) {
            res.send(404, "Error")
        }
    })
} 