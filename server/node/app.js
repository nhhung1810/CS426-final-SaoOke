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
const port = process.env.PORT || 5000

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

require('./APIs/getAllCampaignAPI')(app, campaignFactory)

require('./APIs/transactionlogAPI')(app, mCoin)

require('./APIs/cpnCreateAPI')(app, userFactory, campaignFactory)

require('./APIs/getCpnInfoAPI')(app, campaignFactory)

require('./APIs/requestHelpAPI')(app, campaignFactory)

require('./APIs/donateAPI')(app, userFactory, campaignFactory, mCoin)

require('./APIs/giveAPI')(app, userFactory, campaignFactory, mCoin)

require('./APIs/getRequestHelpListAPI')(app, campaignFactory)

require('./APIs/getAllCampaignAPI')(app, campaignFactory)

require('./APIs/getCamByUserAPI')(app, campaignFactory)

require('./APIs/publicAPI')(app, userFactory, campaignFactory)




// GET: CheckHelpRequests => Danh sách các người đã đăng yêu cầu từ thiện
require('./APIs/getDonatorsAPI')(app, campaignFactory, userFactory)

require('./APIs/getHistoryAPI')(app, campaignFactory, userFactory)

