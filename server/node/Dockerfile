FROM node:10.10-alpine


COPY . /app

WORKDIR /app

# RUN npm init -y
RUN npm install --save -g elliptic crypto-js debug nodemon
RUN npm install express --save
# RUN npm install -g nodemon

CMD ["nodemon", "app.js"]

