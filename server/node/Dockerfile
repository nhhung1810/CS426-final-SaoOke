FROM node:10.10

WORKDIR /app

COPY . .

RUN npm init -y
RUN npm install elliptic crypto-js debug

CMD ["node", "app.js"]

