package ch.sachi.services.customers.persistence;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRepositoryImpl implements ch.sachi.services.customers.CustomerRepository {
    private final JpaCustomerRepository jpaRepo;

    public CustomerRepositoryImpl(JpaCustomerRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jpaRepo.findAll();
    }
}
