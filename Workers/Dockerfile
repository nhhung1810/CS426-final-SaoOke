FROM golang:1.16-alpine
ARG URL=http://server:5000
ENV ENDPOINT_URL=${URL}

COPY . /workers

WORKDIR /workers

CMD ["go", "run", "Worker.go"]