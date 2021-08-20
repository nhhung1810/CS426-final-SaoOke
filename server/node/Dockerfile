FROM node:10.10-alpine


COPY . /app

WORKDIR /app

RUN npm install
CMD ["npx", "nodemon", "app.js"]

