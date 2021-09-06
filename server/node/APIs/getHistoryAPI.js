// GET: CheckHistory(campaign) => trả về các lượt donate và các lượt give của chiến dịch
module.exports = function(app, campaignFactory) {
    app.get('/getCampaignHistory/:campaignName', function (req, res) {
        if (!req.params || !req.params.campaignName) {
            res.send(404, {"message" : "params invalid"})
        }

        try {
            res.send(200, campaignFactory.getCampaignHistory(req.params.campaignName))
        } catch (err) {
            res.send(404, {"message" : err.message})
        }
    })
}