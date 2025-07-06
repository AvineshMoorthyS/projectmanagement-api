package com.projectmanagement.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
@Component
public class KeepWarmController implements ApplicationListener<ApplicationReadyEvent> {

    private WebClient webClient;
    private boolean appStarted = false;

    @Value("${ENDPOINT}")
    private String endPoint;

    private final WebClient.Builder webClientBuilder;

    public KeepWarmController(WebClient.Builder webClientBuilder) {
        log.info("Inside Keep Warm Controller Constructor");
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder
                .baseUrl(endPoint)
                .build();
        log.info("WebClient initialized with endpoint: {}", endPoint);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        appStarted = true;
        log.info("✅ Application is ready. Warm-up task will now start running.");
    }

    @Scheduled(fixedDelay = 240_000)
    public void warmUp() {
        if (!appStarted) return;

        webClient.get()
                .uri("/lambdawarm")
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("🔥 Warmup success: {}", response))
                .doOnError(error -> log.error("❌ Warmup failed: {}", error.getMessage()))
                .subscribe();
    }

    @GetMapping("/lambdawarm")
    public String lambdaWarm() {
        return "Lambda Warm";
    }
}
