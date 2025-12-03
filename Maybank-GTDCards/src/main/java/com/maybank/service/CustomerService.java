package com.maybank.service;

import com.maybank.client.*;
import com.maybank.dto.CreateCustomerRequest;
import com.maybank.exception.ResourceNotFoundException;
import com.maybank.model.Customer;
import com.maybank.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository repo;

  @Transactional(readOnly = true)
  public Customer getById(Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
  }

  @Transactional(readOnly = true)
  public Page<Customer> listPaged(int page, int size) {
    return repo.findAll(PageRequest.of(page, size));
  }

  @Transactional
  public Customer create(CreateCustomerRequest req) {
    if (repo.existsByEmail(req.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    Customer c = new Customer();
    c.setName(req.getName());
    c.setEmail(req.getEmail());
    return repo.save(c);
  }

  @Transactional
  public Customer update(Long id, CreateCustomerRequest req) {
    Customer c = repo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    // Prevent duplicate email move
    if (!c.getEmail().equalsIgnoreCase(req.getEmail()) && repo.existsByEmail(req.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    c.setName(req.getName());
    c.setEmail(req.getEmail());
    return repo.save(c);
  }
}