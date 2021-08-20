FROM node:10.10


COPY . /

WORKDIR /

RUN npm init -y
RUN npm install elliptic crypto-js debug

CMD ["node", "app.js"]

