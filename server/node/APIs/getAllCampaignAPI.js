// GET: CheckAllCampaigns => lấy tất cả thông tin chiến dịch sẵn có
module.exports = function(app, campaignFactory) {
    app.get("/campaigns", function (req, res) {
        try {
            res.send(200, campaignFactory.getAllCampaign())
        } catch (err) {
            res.send(404, "Can not get campaign information")
        }
    })
}