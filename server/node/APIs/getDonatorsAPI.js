// GET: CheckDonator(campaignName) => Danh sách các người donate của chiến dịch
module.exports = function (app, campaignFactory) {
    app.get('/getDonators/:campaignName', function(req, res) {
        if (!req.params || !req.params.campaignName) {
            res.send(404, {"message" : "check your params"})
        }
        
        try {
            res.send(200, campaignFactory.getAllDonators(eq.params.campaignName))
        } catch (err) {
            res.send(404, err.message)
        } 
    })
}