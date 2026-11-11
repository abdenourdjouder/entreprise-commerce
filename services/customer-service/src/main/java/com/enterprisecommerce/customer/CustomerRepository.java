package com.enterprisecommerce.customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();

    public Customer save(Customer customer) {
        customers.put(customer.id(), customer);
        return customer;
    }

    public Optional<Customer> findById(String customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    public List<Customer> findAll() {
        return List.copyOf(customers.values());
    }

    public Optional<Customer> findByEmail(String email) {
        return customers.values().stream()
            .filter(customer -> customer.email().equalsIgnoreCase(email))
            .findFirst();
    }

    public Customer create(String firstName, String lastName, String email) {
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, email, firstName, lastName);
        return save(customer);
    }
}
