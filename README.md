# CS426-final-SaoOke

From past to present, charity fundraising has always been an expression of the solidarity spirit of Vietnamese. A lot of people take part in the charity wave, and as a consequence, there are many cases where celebrities use their popularity and influence to call for a fundraising and take all or part of the money for personal uses. And those are just a little part of the inadequacy of the problem of charity fundraising.

One of the ways to authenticate donations or fund use is checking bank statement of the fundraising. However, when it comes to a large fundraising campaign, the number of donations and fund uses can be very huge, which leads to the making of the statement can be very hard and time-consuming.

Because of the disadvantages of bank statements, we came up with an idea of a charity management app in which every donation, every use of the fund can be absolutely trusted.
SaoOke is a charity management project where users can either create fundraising campaigns, donate to some or even send help requests. We provide not only a “sao kê” (bank statement), but a “sao oke”. In this project, we’ve tried using blockchain to create a decentralized app (dApp), which provides better security and authentication, so that each activity like fundraising, donating or fund using can be guaranteed.

So, let’s jump into the details.

“Sao? Oke?"

(What? Oke?)

Feel free to check out our [demo video](https://youtu.be/SkXHbQrREQQ) and [detailed report](https://docs.google.com/document/d/1k6IF82hUnx0FUkO_HAzOgmrgECV37aHZ3wP1frZSkBQ/edit?usp=sharing)!

## Key Technologies
`Java`, `Android Studio`, `Retrofit`, `Node.js`, `Blockchain`, `REST API`, `Cryptocurrency`, `Docker`, `Golang`

## Project Structure

Please note that this project is still in development. The blockchain is partially implemented, there are no P2P or distributed system. Because of limited resources, we simulate the "P2P" by pushing the Blockchain to a server, and build API for everyone to connect. However, we still implement some basic features, including the signature verification, blockchain validation, and the API is well fitted for our purpose. If you find this useful, you can reimplement the API to fit your purpose.

The structure:

1. Server: contains the Blockchain, encapsulate the user (username and public key in PEM format), the campaign (specially defined for our purpose), and provides API for our mobile clients to interact with; built with Node.js.
2. Mobile Client: built with Java for Android users; served as the main interface to interact with the server.
3. Bots: As we want to simulate the multiple-users scenarios, we built bots in Golang.

## Server Deployment

Prerequisite: `Operating System: Windows 10`
1. Install docker-compose: https://docs.docker.com/compose/install/
2. cd to the directory, run command:
````console
docker-compose build
docker-compose up
````
3. Open project 'Client' in Android Studio and build
4. Run

### Error may occurs in deployment

- In runtime, the server may run with missing dependency/lib error, you can cd to server/node and run the following command to fix this
````console
npm install <missing-lib>
````
- If Docker Desktop (in Windows 10) doesn't start properly, there may be a virtualization service conflict between Docker and Virtual Device (VD). This problem has occured when I install Nox Player as an alternative virtual device to Android Virtual Device (AVD). If you did install Nox or any alternative VDs, go to start menu and search for vitrualization features and turn it on (as Nox turn it off) and restart 1-2 times an check Docker Desktop again. It should functions properly by now.

## App Flow
<p align="center">
  <img src="https://user-images.githubusercontent.com/29631037/132642548-a44503ca-17ec-48af-9871-f1da4ce9518b.png" alt="SaoOke's App Flow 1" width="700dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132642603-2c2e6bf2-c05e-47ef-baac-599674326f14.png" alt="SaoOke's App Flow 2" width="700dp"/>
</p>

## Screenshots
  

<p align="center">
  <img src="https://user-images.githubusercontent.com/29631037/132644140-905349ec-c6bb-4c68-abd2-9a07aee4ae3c.png" alt="Screenshot 1" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644146-f0969153-eb32-4248-a009-c055fa97cb89.png" alt="Screenshot 2" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644157-1c8a1a45-9400-4acb-b15a-7c8cea1fba61.png" alt="Screenshot 3" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644162-d14f4e62-60e4-4af4-a2e1-93d75d542df2.png" alt="Screenshot 4" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644170-c02c266c-9292-4bc7-a21f-7ed77896038e.png" alt="Screenshot 5" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644175-dccf59a6-0132-468d-8153-642fcbcb8ec1.png" alt="Screenshot 6" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644185-6587477a-c439-4ab5-aed5-5d3f8ab5ced9.png" alt="Screenshot 7" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644187-d75b04c5-8514-47b0-b7ee-77efaf1ac6f2.png" alt="Screenshot 8" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644189-78f3d296-4159-4c90-a947-8f0ae742fea1.png" alt="Screenshot 9" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644204-0c3c1a03-8e33-4e06-a52b-a4c30307be33.png" alt="Screenshot 10" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644206-75832921-2891-47cf-ae33-e6273f92ef86.png" alt="Screenshot 11" width="200dp"/>
  <img src="https://user-images.githubusercontent.com/29631037/132644211-78cafc53-cfdb-43a5-a969-505233fac48f.png" alt="Screenshot 12" width="200dp"/>
</p>
