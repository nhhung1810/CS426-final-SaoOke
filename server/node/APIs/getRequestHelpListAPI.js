// GET: CheckHelpRequests => Danh sách các người đã đăng yêu cầu từ thiện
module.exports = function(app, campaignFactory) {
    app.get("/checkHelpRequests/:campaignName", function (req, res) {
        if (!req.params) {
            res.send(404, { "message" : "empty param"})
        }
        if (!req.params.campaignName) {
            res.send(404, { "message" : "empty campaign name"})
        }

        try {
            res.send(200, campaignFactory.getRequestHelpList(campaignName))
        } catch (err) {
            res.send(404, {"message" : err.message})
        }
    })
}