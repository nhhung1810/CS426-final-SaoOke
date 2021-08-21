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
        this.userList.forEach(function(value, index) {
            if(username == value.username){
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
        this.makeUser('admin', hashed('admin'))

        //some bots
        this.makeUser('bot1', hashed('bot1'))
        this.makeUser('bot2', hashed('bot2'))
    }


    //get key from username. Again, this is a bad idea, only fit for testing and demo
    getKey(username){
        this.userList.forEach(function(value) {
            if(value.username == username) return value.key
        })
        return null
    }

    //init some money for testing
    freeMoney(username, blockchain, amount){
        key = this.getKey(username)
        blockchain.freeMoney(key.getPublic('hex'), amount)
    }

    authenticate(username, hashedPass){
        this.userList.forEach(function(value){
            if(value.verify(username, hashedPass))
                return true;
        })
        return false;
    }

}


module.exports.UserFactory = UserFactory