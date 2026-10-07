package ch.sachi.services.main;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.function.Function;


@Configuration
public class WebClientConfig {
    @Bean
    public ConnectionProvider connectionProvider() {
        LoggerFactory.getLogger(WebClientConfig.class).info("Creating connection provider");
        return ConnectionProvider.builder("load-service")
                .maxConnections(100)
                .maxIdleTime(Duration.ofSeconds(30))
                .maxLifeTime(Duration.ofMinutes(4))
                .metrics(true)
                .build();
    }

    //    }
    @Bean
    public HttpClient httpClient(ConnectionProvider connectionProvider) {
        return HttpClient.create(connectionProvider)
                .metrics(true, Function.identity());
    }


    @Bean
    public WebClient.Builder webClient(HttpClient httpClient) {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
