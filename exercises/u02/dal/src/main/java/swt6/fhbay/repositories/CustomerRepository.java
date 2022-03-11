package swt6.fhbay.repositories;

import swt6.fhbay.domain.Customer;

import java.util.List;

public interface CustomerRepository extends Repository<Customer, Long> {

    List<Customer> findByLastname(String name);
}
