package main

import (
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	r.GET("/load", getLoad)
	r.GET("/healthz", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "ok"})
	})
	r.Run(":8080")
}

func getLoad(c *gin.Context) {
	hostname, _ := os.Hostname()
	c.Data(http.StatusOK, "text/plain", []byte(hostname))
}
