FROM --platform=$BUILDPLATFORM golang:1.26.4 AS builder
WORKDIR /app
COPY go.mod go.sum ./
ENV GO111MODULE=on
ENV GOPROXY=https://goproxy.cn
#RUN curl https://proxy.golang.org
#RUN go mod download
COPY . .
RUN GOPROXY=direct CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o load ./load.go
FROM alpine:latest
WORKDIR /root/
COPY ./load .
EXPOSE 8080
CMD ["./load"]