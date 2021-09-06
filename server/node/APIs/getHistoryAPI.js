// GET: CheckHistory(campaign) => trả về các lượt donate và các lượt give của chiến dịch
module.exports = function(app, campaignFactory, userFactory) {
    app.get('/getCampaignHistory/:campaignName', function (req, res) {
        if (!req.params || !req.params.campaignName) {
            res.send(404, {"message" : "params invalid"})
        }

        try {
            transactionLog = campaignFactory.getCampaignHistory(req.params.campaignName)
            transactionLog.map(log => {
                log.from = userFactory.getUsername(log.from)
                log.to = userFactory.getUsername(log.to)
            })
            // transactionLog.forEach(log => {
            //     log.from = userFactory.getHistory(log.from)
            // });
            res.send(200, transactionLog)
        } catch (err) {
            res.send(404, {"message" : err.message})
        }
    })
}