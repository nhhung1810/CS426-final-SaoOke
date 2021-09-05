const { Transaction, Blockchain } = require('./blockchain.js')

const { Verification } = require("./verify")

class User {
    constructor(username, publicKey, role = "donator") {
        this.username = username
        this.publicKey = publicKey
    }

}
class UserFactory {
    constructor() {
        this.userList = []
    }

    makeUser(username, publicKey) {
        this.userList.forEach(function (element) {
            if (username == element.username) {
                throw new Error("Existed this member")
            }
        })

        var isValid = null
        try {
            const verification = new Verification()
            isValid = verification.parseKey(publicKey)
        } catch (error) {
            console.log(error.toString())
            return null
        }

        if (isValid != null) {
            let tmp = new User(username, publicKey)
            this.userList.push(tmp)
            return true
        } else return null;

    }


    //get key from username. Again, this is a bad idea, only fit for testing and demo
    getKey(username) {
        let key = null
        this.userList.forEach((element) => {
            if (element.username === username)
                key = element.publicKey
        })
        return key
    }

    freeMoney(username, amount, blockchain) {
        let key = null
        this.userList.forEach((element) => {
            if (element.username === username)
                key = element.publicKey
        })
        blockchain.freeMoney(key, amount)
    }

}


module.exports.UserFactory = UserFactory