FROM --platform=$BUILDPLATFORM golang:1.26.4 AS builder
WORKDIR /app
COPY go.mod go.sum ./
ENV GO111MODULE=on
RUN go mod download
COPY . .
RUN CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build -o load ./load.go

FROM alpine:latest
WORKDIR /root/
COPY --from=builder /app/load .
EXPOSE 8080
CMD ["./load"]