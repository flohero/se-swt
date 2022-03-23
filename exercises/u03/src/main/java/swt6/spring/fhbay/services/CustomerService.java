package swt6.spring.fhbay.services;

import swt6.spring.fhbay.domain.Customer;
import swt6.spring.fhbay.dtos.CustomerDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDto> saveMany(Customer ...customers);

    List<CustomerDto> findAll();

    Optional<CustomerDto> findById(Long id);
}
