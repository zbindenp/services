package ch.sachi.services.main;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerService {
  private final WebClient webclient;
  private final RestTemplate restTemplate;

  public CustomerService(RestTemplateBuilder builder, @Value("${main.customersbaseurl}") String customersbaseurl) {
    LoggerFactory.getLogger(getClass()).info("Creating our CustomerService");
    restTemplate = builder.rootUri(customersbaseurl).build();
    webclient = WebClient.create(customersbaseurl);
  }

  public List<CustomerInfo> getAllCustomers() {
    final CustomerInfo[] customerInfos = Objects.requireNonNull(webclient.get().uri("/customers").retrieve().toEntity(CustomerInfo[].class).block()).getBody();
//    final CustomerInfo[] customerInfos = Objects.requireNonNull(restTemplate.getForObject("/customers", CustomerInfo[].class));
    assert customerInfos != null;
    return List.of(customerInfos);
  }

  public String getLoad(boolean useWebClient, String conectionHeader) {
    if (useWebClient) {
      Mono<String> result = webclient.get().uri("/load").retrieve().bodyToMono(String.class);
      result.subscribe();
      return result.block();
    } else {
      HttpHeaders headers = new HttpHeaders();
      headers.setConnection(conectionHeader);
      HttpEntity request = new HttpEntity(headers);
      ResponseEntity<String> responseEntity =restTemplate.exchange("/load", HttpMethod.GET, request, String.class);
      String responseHeaders = responseEntity.getHeaders().entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining("++"));
      return responseEntity.getBody() + "                          // " + responseHeaders;
    }
  }
}
