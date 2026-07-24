package main

import (
	"net/http"
	"os"

	"github.com/gin-gonic/gin"
)

func main() {
	router := gin.Default()
	router.GET("/load", getLoad)
	router.GET("/healthz", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"status": "ok"})
	})
	router.Run(":8080")
}

func getLoad(c *gin.Context) {
	hostname, _ := os.Hostname()
	c.Data(http.StatusOK, "text/plain", []byte(hostname))
}
