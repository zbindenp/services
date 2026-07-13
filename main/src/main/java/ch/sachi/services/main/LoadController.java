package ch.sachi.services.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@RestController
public class LoadController {
  private final CustomerService customerService;

  public LoadController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("load")
  public ResponseEntity<Object> getAll(
          @RequestParam(defaultValue = "rt") String type,
          @RequestParam(defaultValue = "keep-alive") String connectionHeader
  ) throws UnknownHostException {
    final Logger logger = LoggerFactory.getLogger(getClass());
    boolean useWebclient = "wc".equalsIgnoreCase(type);
    if (useWebclient) {
      logger.info("LoadController: Start getting customers by WebClient");
    } else {
      logger.info("LoadController: Start getting customers by RestTemplate");
    }
    StringBuilder result = new StringBuilder();
    final LocalDateTime now = LocalDateTime.now();
    result.append("<h2>").append(now).append("</h2>");
    result.append("<h2>LoadController called from ").append(InetAddress.getLocalHost().getHostName()).append(" with ").append(type).append(":</h2>");
    long start = System.currentTimeMillis();
    for (int i = 0; i < 20; i++) {
      final String hostname = customerService.getLoad(useWebclient, connectionHeader);
      logger.info("LoadController: We called [{}] ", hostname);
      result.append(hostname).append("<br/>");
    }
    long durationMillis = System.currentTimeMillis() - start;
//    result.append("<br/>Duration: ").append(durationMillis).append("ms");
    logger.info("LoadController: -------------------");
    return ResponseEntity.ok(result);
  }
}

