package com.payu.catalogui.configs;

import com.payu.catalogui.client.BookClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

@Configuration
public class BookClientConfig {

    private static final String BASE_URL = "http://localhost:8080/api/books";

    @Bean
    public Client jerseyClient() {
        return JerseyClientBuilder
                .newBuilder()
                .register(JsonBindingFeature.class)
                .build();
    }

    @Bean
    public WebTarget bookWebTarget(Client jerseyClient) {
        return jerseyClient.target(BASE_URL);
    }

    @Bean
    public BookClient bookClient(WebTarget bookWebTarget) {
        return new BookClient(bookWebTarget);
    }
}
