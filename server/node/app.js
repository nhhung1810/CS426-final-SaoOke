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

<<<<<<< HEAD
//param: address = username
app.post("/balance/:username", function(req, res){
  if(!req.params | !req.params.username){
    res.send(404, {"error" : "Invalid params"})
  }
  else {
    //remerber fix this pls, this should be accept an public key
    try {
      console.log("Exception! Check if user existed?")
      var publicKey = userFactory.getKey(req.params.username);
      console.log("\nGet balance of:\n", publicKey, "\n")
      var tmp = mCoin.getBalanceOfAddress(publicKey)
      res.send(200, tmp)
    } catch (error) {
      res.send(403, {"error" : "Unable to find user"})
    }
    
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
    // console.log(req.body)
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
      ///backdoor for bots, because can not find rsa library support pkcs8 in golang
      /*
      if (req.body.isbot) {
          tx.isValid = () => { return true;}
      }
      */
      if (!tx.isValid()) {
        res.send(405, {"error" : "Invalid signature"})
        return;
      }
    } catch(err) {
      console.log(err)
      res.send(405, {"error" : "Invalid sign key. Check your 'from' key"})
      return;
    }
=======
require('./APIs/balanceAPI')(app)

require('./APIs/transactionAPI')(app)
>>>>>>> origin/hung-api2

require('./APIs/registerAPI')(app)

require('./APIs/transactionlogAPI')(app)


// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, message)
// POST: Donate (như transaction) (donatorKey, campaignName, amount, message) => Chuyển tiền bằng public key tới owner của cái campaign đó
// POST: Give (như transaction) (campaignOwnerKey, receiverKey, amount ,message)
// POST: RequestHelp (ownerKey, amount, message)
// GET: CheckCampaignInformation (campaignName) => trả về tất cả thông tin kèm theo số tiền trong chiến dịch
// GET: CheckHistory(campaign) => trả về các lượt donate và các lượt give của chiến dịch
// GET: CheckAllCampaigns => lấy tất cả thông tin chiến dịch sẵn có
// GET: CheckDonator(campaignName) => Danh sách các người donate của chiến dịch
// GET: CheckHelpRequests => Danh sách các người đã đăng yêu cầu từ thiện

