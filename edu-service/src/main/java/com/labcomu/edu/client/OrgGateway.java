package com.labcomu.edu.client;

import com.labcomu.edu.configuration.EduProperties;
import com.labcomu.edu.resource.Organization;
import com.labcomu.faultinjection.annotation.Delay;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class OrgGateway {
    private final String fetchOrganizationUrl;

    private final WebClient.Builder webClientBuilder;

    public OrgGateway(final WebClient.Builder webClientBuilder,
            final EduProperties properties) {
        this.webClientBuilder = webClientBuilder;
        this.fetchOrganizationUrl = properties.getUrl().getFetchOrganizationDetails();
    }

    @CircuitBreaker(name = "eduService", fallbackMethod = "getLog")
    public Organization getOrganization(@NotNull final String url) {
        return webClientBuilder.build()
                .get()
                .uri(fetchOrganizationUrl, url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Organization.class)
                .block();
    }

    public Organization getLog (RuntimeException e){
        Organization org = new Organization();
        org.setName(null);
        org.setUrl(null);
        System.out.println(HttpStatus.UNPROCESSABLE_ENTITY + ": o org service não respondeu de forma esperada.");
        return org;
    }
}
