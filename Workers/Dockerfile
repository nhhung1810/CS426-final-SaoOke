FROM golang:1.16-alpine
ARG a=c
COPY . /workers

WORKDIR /workers

CMD ["go", "run", "Worker.go", "5"]