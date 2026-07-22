package ch.sachi.services.main;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.stream.Collectors;

@Service
public class LoadService {
    private final WebClient webclient;
    private final RestTemplate restTemplate;

    public LoadService(RestTemplateBuilder builder, @Value("${main.loadbaseurl}") String loadbaseurl) {
        LoggerFactory.getLogger(getClass()).info("Creating our CustomerService");
        restTemplate = builder.rootUri(loadbaseurl).build();
        webclient = WebClient.create(loadbaseurl);
    }

    public LoadResponse getLoad(boolean useWebClient, String conectionHeader) {
        long start = System.nanoTime();
        if (useWebClient) {
            final ResponseEntity<String> response = webclient.get().uri("/load").retrieve().toEntity(String.class).block();
            return createLoadResponse(response, start);
        } else {
            final HttpHeaders headers = new HttpHeaders();
            headers.setConnection(conectionHeader);
            final HttpEntity request = new HttpEntity(headers);
            final ResponseEntity<String> response = restTemplate.exchange("/load", HttpMethod.GET, request, String.class);
            return createLoadResponse(response, start);
        }
    }

    @NonNull
    private static LoadResponse createLoadResponse(ResponseEntity<String> responseEntity, long startNanos) {
        final String responseHeaders = responseEntity.getHeaders()
                .entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("// "));
        return new LoadResponse(responseEntity.getBody(), System.nanoTime() - startNanos, responseHeaders);
    }
}
