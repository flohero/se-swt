package swt6.spring.fhbay.services;

import swt6.spring.fhbay.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> saveMany(Customer ...customers);

    List<Customer> findAll();

    Optional<Customer> findById(Long id);
}
