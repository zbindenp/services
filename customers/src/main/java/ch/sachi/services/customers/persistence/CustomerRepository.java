package ch.sachi.services.customers.persistence;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRepository implements ch.sachi.services.customers.CustomerRepository {
    private final JpaCustomerRepository jpaRepo;

    public CustomerRepository(JpaCustomerRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jpaRepo.findAll();
    }
}
