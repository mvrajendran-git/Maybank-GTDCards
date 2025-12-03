package com.maybank.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;

import com.maybank.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);
  boolean existsByEmail(String email);
}