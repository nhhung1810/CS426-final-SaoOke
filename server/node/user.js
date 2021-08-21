// const crypto = require("crypto")
const EC = require('elliptic').ec;
const ec = new EC('secp256k1');

class User{
    constructor(username, hashedPass){
        this.username = username
        this.hashedPass = hashedPass
        //Really dangerous, this is not practical and by no mean can be use in real life. Plz don't judge :(((((
        this.key = ec.genKeyPair()
    }
}

class UserFactory{
    constructor(){
        this.userList = []
    }

    makeUser(username, hashedPass){
        this.userList.forEach(function(value, index) {
            if(username == value.username){
                throw new Error("Existed this member")
            }
        })
        tmp = new User(username, hashedPass)
        this.userList.push(tmp)
        return tmp.key
    }
}

module.exports.UserFactory = UserFactory