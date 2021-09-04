const express = require('express')
const { Blockchain, Transaction } = require('./blockchain');
const { UserFactory } = require('./user')
const { Verification } = require("./verify")
// const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

const mCoin = new Blockchain();
const userFactory = new UserFactory()

//test
// const verification = new Verification()
// pub = verification.parseKey("public_key.pem", true)
// pri = verification.parseKey("private_key_pkcs8.pem", true)
// myWalletAddress = ver.keyToString(pub)

const app = express()
const port = 5000

app.use(express.json()) // for parsing application/json

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
  // test()
})

//param: address = publicKey
app.post("/balance", function(req, res){
  if(!req.body || !req.body.address || req.body.address.length === 0){
    res.sendStatus(404)
  }
  else {
    //remerber fix this pls, this should be accept an public key
    tmp = mCoin.getBalanceOfAddress(req.body.address)
    res.send(200, tmp)
  }

})

// Route: /transaction
// Method: POST
// json sent via body
// For example:
// {
//   "from" : "admin",
//   "to" : "hung",
//   "amount" : 10,
//   "signature" : "ijA1JX6jnyd487Ba3ZsYCaan2XnuIqXkmx98HGpwFQkpzwzaZ3WskbJyFMGJkyogDYrnPq
//        j+kCHL+qNJTEwE1gOsS3SWdG6+t78ce6eT0xFkJMS7N0Guu20ln9StCOio4pnKNz0ULH3epCn2VpfsDeS4/HcDJc4vKF2mUk1whM0="
// }
app.post('/transaction', function(req, res){
  const trans = (req) => {
    console.log(req.body)
    if(!req || !req.body || !req.body.transaction || !req.body.signature) {
      console.log("How about null body");
      return null;
    }
    if(!req.body.from || !req.body.to || !req.body.amount || !req.body.signature){
          console.log("Invalid transaction params")
          return null
        }
    else {
      try{
        publicKey = userFactory.getKey(req.body.from);
        console.log(publicKey)
        console.log("New transaction is been created")
        return new Transaction(publicKey, 
                                req.body.to, 
                                req.body.amount,
                                req.body.signature)
      } catch(ex){
        console.log(ex.toString())
        return null
      }
      
    }
  }

  tx = trans(req)
  if(tx === null){
    res.send(404, {"error" : "Invalid params. Null key"})
  } else {
    // console.log(tx.signTransaction)
    try {
      // publicKey = verification.parseKey(userFactory.getKey(req.body.from))
      // console.log(publicKey)
      if (!tx.isValid()) {
        res.send(405, {"error" : "Invalid signature"})
        return;
      }
    } catch(err) {
      console.log(err)
      res.send(405, {"error" : "Invalid sign key. Check your 'from' key"})
      return;
    }

    console.log("\nPassed the valid check!!\n")
    mCoin.addTransaction(tx)
    res.send(200, {"status" : "success"});
  }
  
})

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

app.post('/register', function(req, res){
  const register = (req) => {
    if(!req || !req.body) return null;
    if(!req.body.username || !req.body.publicKey) return null;
    else {
      return userFactory.makeUser(req.body.username, req.body.publicKey)
    }
  }

  try {
    flag = register(req)
    if(flag === null) res.send(404, {
      "error" : "Invalid register process. Check your params"
    })  
    else{
      userFactory.freeMoney(req.body.username, 1000, mCoin)
      res.send(200, {
        "status" : "success"
      })
    } 
  } catch(err) {
    //case: error at the makeUser, maybe the username is existed
    console.log(err)
    res.send(405, {
      "error" : "Check your username. It may existed"
    })
  }
})

app.get('/transactionsLog', function(req, res) {
  var transactions = mCoin.getTransactionsLog()
  res.send(200, 
    {
      "logs": transactions
    }  
  )
})

//add free money for further test, pass 
// app.post('/free', function(req, res){
//   const trans = (req) => {
//     if(!req || !req.body || !req.body.username || !req.body.amount)
//       return null
//     userFactory.freeMoney(req.body.username, mCoin, req.body.amount)
//     return true
//   }

//   tmp = trans(req)
//   if(tmp === true){
//     res.send(200, {
//       "status" : "success"
//     })
//   } else {
//     res.send(404, {
//       "error" : "Invalid, check your params"
//     })
//   }
// })


// app.post('/login', function(req, res){
//   const check = (req) =>{
//     if(!req || !req.body || !req.body.username || !req.body.password)
//       return null
//     else {
//       return userFactory.authenticate(req.body.username, req.body.password)
//     }
//   }

//   flag = check(req)
//   if(flag === null){
//     res.send(404, {
//       "error" : "Invalid request. Check your params"
//     })
//   } else {
//     if(flag === true) res.send(200, {
//       "status" : "success"
//     }) 
//     else {
//       res.send(405, {
//         "error" : "Invalid Login. Check your username and password"
//       })
//     }
//   }
// })

app.post('/mine', (req, res) => {
  console.log(req.body)
  if (!req || !req.body || !req.body.address) {
    res.send(404, {
      "status" : "failed",
      "error" : "Invalid request. Check your params"
    })
  } else {
    address = req.body.address
    mCoin.minePendingTransactions(address)
    res.send(200, {
      "status" : "success"
    }) 
  }
})



/*
TEST SPACE
*/
test = () => {
    // Mine first block
  mCoin.minePendingTransactions(myWalletAddress);

  // Create a transaction & sign it with your key
  const tx1 = new Transaction(myWalletAddress, 'address2', 100);
  tx1.signTransaction(myKey);
  mCoin.addTransaction(tx1);

  // Mine block
  mCoin.minePendingTransactions(myWalletAddress);

  // Create second transaction
  const tx2 = new Transaction(myWalletAddress, 'address1', 50);
  tx2.signTransaction(myKey);
  mCoin.addTransaction(tx2);

  // Mine block
  mCoin.minePendingTransactions(myWalletAddress);

  console.log();
  console.log(`Balance of xavier is ${mCoin.getBalanceOfAddress(myWalletAddress)}`);

  // Uncomment this line if you want to test tampering with the chain
  // mCoin.chain[1].transactions[0].amount = 10;

  // Check if the chain is valid
  console.log();
  console.log('Blockchain valid?', mCoin.isChainValid() ? 'Yes' : 'No');
};


app.post('/testkey', (req, res) => {
  // var publicKey = req.body.publicKey
  // var signature = req.body.signature

  // var key = ec.keyFromPublic(publicKey, "hex")
  var a = 123
  res.send(200, String("sdfsaf"))
})