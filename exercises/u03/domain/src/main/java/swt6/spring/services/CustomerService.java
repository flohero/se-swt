package swt6.spring.services;



import swt6.spring.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> saveMany(Customer...customers);

    Customer save(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(Long id);
}
