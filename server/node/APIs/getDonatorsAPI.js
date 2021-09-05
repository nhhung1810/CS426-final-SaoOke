// GET: CheckDonator(campaignName) => Danh sách các người donate của chiến dịch
module.exports = function (app, campaignFactory, userFactory) {
    app.get('/getDonators/:campaignName', function (req, res) {
        if (!req.params || !req.params.campaignName) {
            res.send(404, { "message": "check your params" })
        }
        var tmp
        try {
            // console.log("\n\nCampaign name: ", req.params.campaignName)
            tmp = campaignFactory.getAllDonators(req.params.campaignName, userFactory)
            if (tmp == null || tmp.length < 1) {
                res.send(404, "error")
                return
            }
        } catch (err) {
            res.send(404, err.message)
            return
        }

        res.send(200, tmp)
    })
}