package swt6.spring.fhbay.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import swt6.spring.fhbay.domain.Customer;
import swt6.spring.fhbay.dtos.CustomerDto;
import swt6.spring.fhbay.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDto> saveMany(Customer... customers) {
        return map(customerRepository.saveAll(List.of(customers)));
    }

    @Override
    public List<CustomerDto> findAll() {
        return map(customerRepository.findAll());
    }

    @Override
    public Optional<CustomerDto> findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(c -> mapper.map(c, CustomerDto.class));
    }

    private List<CustomerDto> map(List<Customer> customers) {
        return customers.stream().map(c -> mapper.map(c, CustomerDto.class)).toList();
    }
}
