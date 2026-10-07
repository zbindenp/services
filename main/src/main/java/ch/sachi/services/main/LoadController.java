package ch.sachi.services.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoadController {
    private final LoadService loadService;

    public LoadController(LoadService loadService) {
        this.loadService = loadService;
    }

    @GetMapping("load")
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "rt") String type,
            @RequestParam(defaultValue = "keep-alive") String connectionHeader,
            @RequestParam(name = "count", defaultValue = "20") String countRequested,
            @RequestParam(defaultValue = "0") long sleepMillis
    ) throws UnknownHostException {
        final Logger logger = LoggerFactory.getLogger(getClass());
        boolean useWebclient = "wc".equalsIgnoreCase(type);
        if (useWebclient) {
            logger.info("LoadController: Start getting load by WebClient");
        } else {
            logger.info("LoadController: Start getting load by RestTemplate");
        }
        int count = Integer.parseInt(countRequested);
        List<Mono<LoadResponse>> monos = new ArrayList<>(count);
        final long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Mono<LoadResponse> mono = loadService.callTimedLoad(useWebclient, connectionHeader, sleepMillis);
            monos.add(mono);
        }
        List<LoadResponse> responses = Flux.merge(monos).collectList().block();
        StringBuilder result = new StringBuilder();
        final LocalDateTime now = LocalDateTime.now();
        result.append("<h2>").append(now).append("</h2>");
        result.append("<h2>LoadController called [").append(countRequested).append("] times from ").append(InetAddress.getLocalHost().getHostName()).append(" with ").append(type).append(":</h2>");
        result.append("<table border=\"1\"><tr><th>pod</th><th>Duration [ms]</th><th>response</th></tr>");
        for (LoadResponse response : responses) {
            result.append("<tr>");
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

