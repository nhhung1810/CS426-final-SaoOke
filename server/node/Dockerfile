FROM node:10.10-alpine


COPY . /app

WORKDIR /app

# RUN npm init -y
RUN npm install --save elliptic crypto-js debug
RUN npm install -g nodemon

CMD ["nodemon", "app.js"]

