const express = require('express')
const { Blockchain, Transaction } = require('./blockchain');
const { UserFactory } = require('./user')
const { Verification } = require("./verify")
// const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

const mCoin = new Blockchain();
const userFactory = new UserFactory()

//test
ver = new Verification;
pub = ver.parseKey("public_key.pem", true)
pri = ver.parseKey("private_key_pkcs8.pem", true)
myWalletAddress = ver.keyToString(pub)

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
    tmp = mCoin.getBalanceOfAddress(myWalletAddress)
    res.send(200, tmp)
  }

})

// Route: /transaction
// Method: POST
// json sent via body
// {"transaction": {"from" : "privateKey", "to" : "", "amount" :" "}}
// For example:
// {
//   "transaction" : {
//       "from" : "8955c93d5e5a33af207eed4907ec608ae85fbff89a6b6f795d36a49b26e29b01", (BASE64)
//       "to" : "someone",
//       "amount" : 10
//   },
//   "signature" : "0xasdfsdf", ///Calculated by (from + to + amount) => base64/hex
// }
app.post('/transaction', function(req, res){
  const trans = (req) => {
    if(!req || !req.body || !req.body.transaction || !req.body.signature) {
      console.log("How about null body");
      return null;
    }
    if(!req.body.transaction.from 
        || !req.body.transaction.to || !req.body.transaction.amount){
          console.log("Invalid transaction params")
          return null
        }
    else {
      console.log("New transaction is been created")
      return new Transaction(req.body.transaction.from, 
                            req.body.transaction.to, 
                            req.body.transaction.amount,
                            req.body.signature)
    }
  }

  tx = trans(req)
  if(tx === null){
    res.send(404, {"error" : "Invalid params. Null key"})
  } else {
    // console.log(tx.signTransaction)
    try {
      if (!tx.isValid()) {
        res.send(405, {"error" : "Invalid signature"})
      }
    } catch(err) {
      console.log(err)
      res.send(405, {"error" : "Invalid sign key. Check your 'from' key"})
    }
    
    mCoin.addTransaction(tx)
    res.send(200, {"status" : "success"});
  }
  
})

// Route: /register
// Method: POST
// json sent via body
// {username: "", password: ""}
// app.post('/register', function(req, res){
//   const register = (req) => {
//     if(!req || !req.body) return null;
//     if(!req.body.username || !req.body.password) return null;
//     else {
//       return userFactory.makeUser(req.body.username, req.body.password)
//     }
//   }

//   try {
//     key = register(req)
//     if(key === null) res.send(404, {
//       "error" : "Invalid register process. Check your params"
//     })  
//     else res.send(200, {
//       "publicKey": key.getPublic('hex'),
//       "privateKey": key.getPrivate('hex')
//     })
//   } catch(err) {
//     //case: error at the makeUser, maybe the username is existed
//     console.log(err)
//     res.send(405, {
//       "error" : "Check your username. It may existed"
//     })
//   }
  

// })

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
// test = () => {
//     // Mine first block
//   mCoin.minePendingTransactions(myWalletAddress);

//   // Create a transaction & sign it with your key
//   const tx1 = new Transaction(myWalletAddress, 'address2', 100);
//   tx1.signTransaction(myKey);
//   mCoin.addTransaction(tx1);

//   // Mine block
//   mCoin.minePendingTransactions(myWalletAddress);

//   // Create second transaction
//   const tx2 = new Transaction(myWalletAddress, 'address1', 50);
//   tx2.signTransaction(myKey);
//   mCoin.addTransaction(tx2);

//   // Mine block
//   mCoin.minePendingTransactions(myWalletAddress);

//   console.log();
//   console.log(`Balance of xavier is ${mCoin.getBalanceOfAddress(myWalletAddress)}`);

//   // Uncomment this line if you want to test tampering with the chain
//   // mCoin.chain[1].transactions[0].amount = 10;

//   // Check if the chain is valid
//   console.log();
//   console.log('Blockchain valid?', mCoin.isChainValid() ? 'Yes' : 'No');
// };


// app.post('/testkey', (req, res) => {
//   // var publicKey = req.body.publicKey
//   // var signature = req.body.signature

//   // var key = ec.keyFromPublic(publicKey, "hex")
//   var a = 123
//   res.send(200, String("sdfsaf"))
// })