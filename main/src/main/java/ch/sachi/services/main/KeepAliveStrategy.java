package ch.sachi.services.main;

import org.apache.hc.client5.http.ConnectionKeepAliveStrategy;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.util.TimeValue;
import org.slf4j.LoggerFactory;

public class KeepAliveStrategy implements ConnectionKeepAliveStrategy {
  @Override
  public TimeValue getKeepAliveDuration(HttpResponse response, HttpContext context) {
    LoggerFactory.getLogger(getClass()).info("KeepAliveDuration is always null");
    return null;
  }
}
