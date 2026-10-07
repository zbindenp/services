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
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Collectors;

@Service
public class LoadService {
    private final WebClient webclient;
    private final RestTemplate restTemplate;

    public LoadService(
            RestTemplateBuilder builder,
            WebClient.Builder webClientBuilder,
            @Value("${main.loadbaseurl}") String loadbaseurl
    ) {
        LoggerFactory.getLogger(getClass()).info("Creating our CustomerService");
        restTemplate = builder.rootUri(loadbaseurl).build();
        webclient = webClientBuilder.baseUrl(loadbaseurl).build();
    }

    Mono<LoadResponse> callTimedLoad(boolean useWebClient, String conectionHeader, long sleepMillis) {
        return prepareCall(useWebClient, conectionHeader, sleepMillis)
                .timed()
                .map(timedResult ->
                        createLoadResponse(timedResult.get(), timedResult.elapsed()));
    }

    private Mono<ResponseEntity<String>> prepareCall(boolean useWebClient, String conectionHeader, long sleepMillis) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setConnection(conectionHeader);
        final String uri = "/load?sleepMillis=" + sleepMillis;
        if (useWebClient) {
            return webclient.get().uri(uri).retrieve().toEntity(String.class);
        }
        final HttpEntity<String> request = new HttpEntity<>(headers);
        return Mono.fromCallable(() -> restTemplate.exchange(uri, HttpMethod.GET, request, String.class));
    }


    @NonNull
    private static LoadResponse createLoadResponse(ResponseEntity<String> responseEntity, Duration duration) {
        final String responseHeaders = responseEntity.getHeaders()
                .entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("// "));
        return new LoadResponse(responseEntity.getBody(), duration.toNanos(), responseHeaders);
    }
}
