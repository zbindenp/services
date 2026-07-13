package ch.sachi.services.main;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoadScheduler {
  private final CustomerService customerService;
  private final Tracer tracer;

  public LoadScheduler(CustomerService customerService, OpenTelemetry otel) {
    this.customerService = customerService;
    this.tracer = otel.getTracer("LoadScheduler");
  }

  @Scheduled(fixedRate = 600 * 1000)
  void load() {
    final Logger logger = LoggerFactory.getLogger(getClass());
    Span span = tracer.spanBuilder("schedule").setNoParent().startSpan();
    boolean useWebclient = (System.currentTimeMillis() % 2 == 0);
    try (Scope scope = span.makeCurrent()) {
      if (useWebclient) {
        logger.info("Scheduled: Start getting customers by WebClient");
      } else {
        logger.info("Scheduled: Start getting customers by RestTemplate");
      }
      for (int i = 0; i < 20; i++) {
        final String hostname = customerService.getLoad(useWebclient, "keep-alive");
        logger.info("Scheduled: We called [{}] ", hostname);
      }
      logger.info("Scheduled: -------------------");
    } finally {
      span.end();
    }
  }
}
