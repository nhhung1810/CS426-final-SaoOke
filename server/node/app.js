const express = require('express')
const { Blockchain, Transaction } = require('./blockchain');
const { UserFactory } = require('./user')
const EC = require('elliptic').ec;
const ec = new EC('secp256k1');
const debug = require("debug")("main:debug")


const mCoin = new Blockchain();
const myKey = ec.keyFromPrivate('8955c93d5e5a33af207eed4907ec608ae85fbff89a6b6f795d36a49b26e29b01');
const myWalletAddress = myKey.getPublic('hex');


const app = express()
const port = 5000

app.use(express.json()) // for parsing application/json

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
  test()
})

//param: address = publicKey
app.post("/history", function(req, res){
  if(!req.body || !req.body.address || req.body.address.length === 0){
    res.sendStatus(404)
  }
  else {
    //remerber fix this pls, this should be accept an public key
    tmp = mCoin.getBalanceOfAddress(myWalletAddress)
    res.send(200, `Balance of this wallet is ${JSON.stringify(tmp)}`)
  }
})

// Route: /transaction
// Method: POST
// json sent via body
// {"transaction": {"from" : "privateKey", "to" : "", "amount" :" "}}
app.post('/transaction', function(req, res){
  let tx = (req) => {
    if(!req || !req.body || !req.body.transaction) {
      console.log("How about null body");
      return null;
    }
    if(!req.body.transaction.from 
        || !req.body.transaction.to || !req.body.transaction.amount){
          console.log("INvalid transaction params")
          return null
        }
    else {
      console.log("New transaction is been created")
      return new Transaction(ec.keyFromPrivate(req.body.transaction.from).getPublic('hex'), 
                            req.body.transaction.to, req.body.transaction.amount)
    }
  }

  tx = tx(req)
  if(tx === null){
    res.sendStatus(404)
  } else {
    // console.log(tx.signTransaction)
    try {
      tx.signTransaction(ec.keyFromPrivate(req.body.transaction.from))
    } catch(err) {
      console.log(err)
      res.send(405, "Invalid sign key. Check your 'from' key")
    }
    
    mCoin.addTransaction(tx)
    mCoin.minePendingTransactions(ec.keyFromPrivate(req.body.transaction.from).getPublic('hex'))
    res.send(200, "Good job");
  }
  
})

// Route: /register
// Method: POST
// json sent via body
// {username: "", password: ""}
app.post('/register', function(req, res){
  const key = (req) => {
    if(!req || !req.body) return null;
    if(!req.body.username || !req.body.hashedPass) return null;
    else 
      return null
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
