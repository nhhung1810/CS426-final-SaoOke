module.exports = function(app, campaignFactory) {
    app.post("/requesthelp", function (req, res) {
        if (!req.body) {
            res.send(404, "Missing params")
        }

        if (!req.body.campaignName) {
            res.send(404, "missing campaign name")
        }

        if (!req.body.username) {
            res.send(404, "missing username")
        }

        if (!req.body.amount) {
            res.send(404, "missing amount")
        }

        if (!req.body.message) {
            res.send(404, "missing message")
        }
        campaignName = req.body.campaignName
        username = req.body.username
        amount = req.body.amount
        message = req.body.message

        try {
            res.send(200, campaignFactory.requestHelp(campaignName, username, amount, message))
        } catch (err) {
            res.send(404, "Can not send request help")
        }
    })
}