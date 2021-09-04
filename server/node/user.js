const {Transaction, Blockchain} = require('./blockchain.js')

const { Verification } = require("./verify")

class User{
    constructor(username, publicKey){
        this.username = username
        this.publicKey = publicKey
    }

}

class UserFactory{
    constructor(){
        this.userList = []
    }
    
    makeUser(username, publicKey){
        this.userList.forEach(function(element) {
        if(username == element.username){
            throw new Error("Existed this member")
        }
        })
        let tmp = new User(username, publicKey)
        console.log("\n//User section\n", tmp)
        this.userList.push(tmp)
        return true
    }


    //get key from username. Again, this is a bad idea, only fit for testing and demo
    getKey(username){
        let key = null
        this.userList.forEach((element) => {
            if(element.username === username) 
                key = element.publicKey
                console.log("\n//Key section\n", key)
        })
        return key
    }

}


module.exports.UserFactory = UserFactory