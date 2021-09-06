# CS426-final-SaoOke

## About this Repo

From past to present, charity fundraising has always been an expression of the solidarity spirit of Vietnamese. A lot of people take part in the charity wave, and as a consequence, there are many cases where celebrities use their popularity and influence to call for a fundraising and take all or part of the money for personal uses. And those are just a little part of the inadequacy of the problem of charity fundraising.

One of the ways to authenticate donations or fund use is checking bank statement of the fundraising. However, when it comes to a large fundraising campaign, the number of donations and fund uses can be very huge, which leads to the making of the statement can be very hard and time-consuming.

Because of the disadvantages of bank statements, we came up with an idea of a charity management app in which every donation, every use of the fund can be absolutely trusted.
SaoOke is a charity management project where users can either create fundraising campaigns, donate to some or even send help requests. We do not provide a “sao kê” (bank statement), but a “sao oke”. In this project, we’ve tried using blockchain to create a decentralized app (dApp), which provides better security and authentication, so that each activity like fundraising, donating or fund using can be guaranteed.

So, let’s jump into the details.

“Sao? Oke?"

(What? Oke?)

## Project Structure

Please note that this project is still in development. The blockchain is partially implemented, there are no P2P or distributed system. Because of resoouce limtting, we simulate the "P2P" by pushing the Blockchain to a server, and build API for eeryone to connect. However, we still implement some basic features, including the signature verification, Blockchain validation, and the API is well fitted for our purpose. If you find this useful, you can reimplement the api to fit your purpose.

The Structure:

1. The Server: contains the Blockchain, encapsulate the user (username and public key in PEM format), the campaign (specially defined for our purpose), and provides API for our mobile clients to interact with. Built with Node.js
2. The Mobile Client: built with Java for Android user. Served as the main interaface to interact with the server.
3. The Bots: as we want to simulate the multiple-users scenarios, we built bots in golang.

## Server Deployment

Prequisite: OS: Windows 10
1. Install docker-compose: https://docs.docker.com/compose/install/
2. cd to the directory, run command:
```
$ docker-compose build
$ docker-compose up
```
3. Open project 'Client' in Android Studio and build
4. Run

### Error may occurs in deployment

- In runtime, the server may run with missing dependency/lib error, you can cd to server/node and run the following command to fix this
```
$ npm install <missing-lib>
```
- If Docker Desktop (in Windows 10) doesn't start properly, there may be a virtualization service conflict between Docker and Virtual Device (VD). This problem has occured when I install Nox Player as an alternative virtual device to Android Virtual Device (AVD). If you did install Nox or any alternative VDs, go to start menu and search for vitrualization features and turn it on (as Nox turn it off) and restart 1-2 times an check Docker Desktop again. It should functions properly by now.

