module.exports = function (app, userFactory, mCoin) {
    // Route: /register
    // Method: POST
    // json sent via body
    // {username: "", publicKey: "The original PEM"}
    // {
    //   "username" : "admin",
    //   "publicKey" : "-----BEGIN PUBLIC KEY-----\nMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDGOll
    //    Tab/mVTMs3353mOBwjDp+\nM6LYYHi+ttH7/diA5PA7ZqJ2NtOzZXWjdaCGrqT/f0vkjWxCzhb1UOGZsSH+jVh
    //    K\niAsag+n2e+xzOPoe7xfWqOn3fI2Rt9yGswJcPP0mHUWsnlOuew9T+yyC7RFEFTX7\nRnD6gyYD8gbWvlFfu
    //    wIDAQAB\n-----END PUBLIC KEY-----"
    // }
    app.post('/register', function (req, res) {
        const register = (req) => {
            if (!req || !req.body) return null;
            if (!req.body.username || !req.body.publicKey) return null;
            else {
                return userFactory.makeUser(req.body.username, req.body.publicKey)
            }
        }

        try {
            var flag = register(req)
            if (flag === null) res.send(404, {
                "error": "Invalid register process. Check your params"
            })
            else {
                userFactory.freeMoney(req.body.username, 1000, mCoin)
                res.send(200, {
                    "status": "success"
                })
            }
        } catch (err) {
            //case: error at the makeUser, maybe the username is existed
            console.log(err)
            res.send(405, {
                "error": "Check your username. It may existed"
            })
        }
    })

}