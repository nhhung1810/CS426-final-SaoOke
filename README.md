# cs426-final-Mcoin

## Deployment

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

## About this Repo
