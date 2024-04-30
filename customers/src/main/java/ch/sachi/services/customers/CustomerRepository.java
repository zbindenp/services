package ch.sachi.services.customers;

import ch.sachi.services.customers.persistence.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAllCustomers();
}
