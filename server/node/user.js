const crypto = require("crypto")
const EC = require('elliptic').ec;
const ec = new EC('secp256k1');
const {Transaction, Blockchain} = require('./blockchain.js')

class User{
    constructor(username, hashedPass){
        this.username = username
        this.hashedPass = hashedPass
        //Really dangerous, this is not practical and by no mean can be use in real life. Plz don't judge :(((((
        this.key = ec.genKeyPair()
    }

    verify(username, hashedPass){
        if(this.username !== username) return false
        else{
            if(this.hashedPass !== hashedPass) return false 
        }
        return true
    }
}

class UserFactory{
    constructor(){
        this.userList = []
        this.initUser()
    }

    makeUser(username, hashedPass){
        this.userList.forEach(function(element) {
            if(username == element.username){
                throw new Error("Existed this member")
            }
        })
        let tmp = new User(username, hashedPass)
        this.userList.push(tmp)
        return tmp.key
    }

    //make some user for testing and for bot
    initUser(){
        const hashed = (password) => {return crypto.createHash("sha256").update(password).digest('hex')}
        //admin
        this.makeUser('admin', "admin")

        //some bots
        this.makeUser('bot1', hashed('bot1'))
        this.makeUser('bot2', hashed('bot2'))
    }


    //get key from username. Again, this is a bad idea, only fit for testing and demo
    getKey(username){
        let key = null
        this.userList.forEach((element) => {
            if(element.username === username) 
                key = (element.key)
        })
        return key
    }

    //init some money for testing
    freeMoney(username, blockchain, amount){
        let key = this.getKey(username)
        blockchain.freeMoney(key.getPublic('hex'), amount)
    }

    authenticate(username, hashedPass){
        let flag = false;
        this.userList.forEach(function(element){
            if(element.verify(username, hashedPass))
                flag = true;
        })
        return flag;
    }

}

// let tmp = new UserFactory()


module.exports.UserFactory = UserFactory