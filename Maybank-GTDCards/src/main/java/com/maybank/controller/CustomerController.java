package com.maybank.controller;

 
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maybank.client.ExchangeRateClient;
import com.maybank.dto.ApiResponse;
import com.maybank.dto.CreateCustomerRequest;
import com.maybank.model.Customer;
import com.maybank.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  @Autowired
  private CustomerService service;

  @Autowired
  private ExchangeRateClient exchangeRateClient;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
     Customer c = service.getById(id);
     return ResponseEntity.ok(new ApiResponse(true, "OK", c));
  }

  @GetMapping
  public ResponseEntity<ApiResponse> list(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size) {
     Page<Customer> result = service.listPaged(page, size);
     return ResponseEntity.ok(new ApiResponse(true, "OK", result));
  }

  @PostMapping
  public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateCustomerRequest req) {
     Customer c = service.create(req);
     return ResponseEntity.ok(new ApiResponse(true, "Created", c));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse> update(@PathVariable Long id,
                                            @Valid @RequestBody CreateCustomerRequest req) {
     Customer c = service.update(id, req);
     return ResponseEntity.ok(new ApiResponse(true, "Updated", c));
  }

  @GetMapping("/{id}/with-rate")
  public ResponseEntity<ApiResponse<Map<String, Object>>> getCustomerWithRate(@PathVariable Long id) {
     Customer c = service.getById(id);
     String rateJson = exchangeRateClient.getUsdInrRateJson();

     Map<String, Object> payload = new HashMap<>();
     payload.put("customerEmail", c.getEmail());
     payload.put("usdInr", rateJson); // or parse into an object if needed

     return ResponseEntity.ok(new ApiResponse<>(true, "OK", payload));
  }

}
