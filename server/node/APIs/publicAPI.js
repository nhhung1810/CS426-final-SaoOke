module.exports = function (app, userFactory) {
    // Route: /public/:username
    // Method: GET
    // Respond as text
    app.get("/public/:username", function(req, res){
        if (!req.params || !req.params.username) {
            res.send(404, "Invalid param")
        }

        try {
            console.log("\nCheck username:", req.params.username)
            var tmp = userFactory.getKey(req.params.username)
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