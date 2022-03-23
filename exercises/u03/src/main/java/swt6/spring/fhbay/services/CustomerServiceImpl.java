package swt6.spring.fhbay.services;

import org.springframework.stereotype.Service;
import swt6.spring.fhbay.domain.Customer;
import swt6.spring.fhbay.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> saveMany(Customer... customers) {
        return customerRepository.saveAll(List.of(customers));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
}
