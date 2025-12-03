package com.maybank.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExchangeRateClient {

  @Autowired
  private WebClient webClient;

  public String getUsdInrRateJson() {
    // Example public API (use a stable endpoint for demo):
    // https://api.exchangerate.host/latest?base=USD&symbols=INR
    return webClient.get()
        .uri("https://api.exchangerate.host/latest?base=USD&symbols=INR")
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }
}