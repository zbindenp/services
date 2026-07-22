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
  private final LoadService loadService;

  public LoadController(LoadService loadService) {
    this.loadService = loadService;
  }

  @GetMapping("load")
  public ResponseEntity<Object> getAll(
          @RequestParam(defaultValue = "rt") String type,
          @RequestParam(defaultValue = "keep-alive") String connectionHeader
  ) throws UnknownHostException {
    final Logger logger = LoggerFactory.getLogger(getClass());
    boolean useWebclient = "wc".equalsIgnoreCase(type);
    if (useWebclient) {
      logger.info("LoadController: Start getting load by WebClient");
    } else {
      logger.info("LoadController: Start getting load by RestTemplate");
    }
    StringBuilder result = new StringBuilder();
    final LocalDateTime now = LocalDateTime.now();
    result.append("<h2>").append(now).append("</h2>");
    result.append("<h2>LoadController called from ").append(InetAddress.getLocalHost().getHostName()).append(" with ").append(type).append(":</h2>");
    final long start = System.currentTimeMillis();
    result.append("<table border=\"1\"><tr><th>pod</th><th>Duration [ms]</th><th>response</th></tr>");
    for (int i = 0; i < 20; i++) {
      result.append("<tr>");
      final LoadResponse response = loadService.getLoad(useWebclient, connectionHeader);
      result.append("<td>").append(response.hostname()).append("</td>");
      logger.info("LoadController: We called [{}] ", response);
      result.append("<td align=\"right\">").append(response.durationMillis()).append("</td>");
      result.append("<td>").append(response.headers()).append("</td>");
      result.append("</tr>");
    }
    result.append("</table>");
    long durationMillis = System.currentTimeMillis() - start;
    result.append("<br/>Duration: ").append(durationMillis).append("ms");
    logger.info("LoadController: -------------------");
    return ResponseEntity.ok(result);
  }
}

