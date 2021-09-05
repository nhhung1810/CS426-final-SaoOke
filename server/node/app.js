const express = require('express')
const { Blockchain, Transaction } = require('./blockchain');
const { UserFactory } = require('./user')
const { CampaignFactory } = require("./campaign")
const { Verification } = require("./verify")
// const rs = require('jsrsasign');
const rsu = require('jsrsasign-util');

const mCoin = new Blockchain();
const userFactory = new UserFactory()
const campaignFactory = new CampaignFactory()


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

require('./APIs/balanceAPI')(app, userFactory, mCoin)

require('./APIs/transactionAPI')(app, userFactory, mCoin)

require('./APIs/registerAPI')(app, userFactory, mCoin)

require('./APIs/transactionlogAPI')(app, mCoin)

require('./APIs/cpnCreateAPI')(app, userFactory, campaignFactory)

require('./APIs/donateAPI')(app, userFactory, campaignFactory, mCoin)

// TODO
// POST: CreateCampaign (ownerKey, campaignName, ownerName, targetAmount, expireDate, message) => DONE

// POST: Donate (như transaction) (donatorKey, campaignName, amount, message) => Chuyển tiền bằng public key tới owner của cái campaign đó
// POST: Give (như transaction) (campaignOwnerKey, receiverKey, amount ,message)
// POST: RequestHelp (ownerKey, amount, message)


// GET: CheckCampaignInformation (campaignName) => trả về tất cả thông tin kèm theo số tiền trong chiến dịch
// GET: CheckHistory(campaign) => trả về các lượt donate và các lượt give của chiến dịch
// GET: CheckAllCampaigns => lấy tất cả thông tin chiến dịch sẵn có
// GET: CheckDonator(campaignName) => Danh sách các người donate của chiến dịch
// GET: CheckHelpRequests => Danh sách các người đã đăng yêu cầu từ thiện
