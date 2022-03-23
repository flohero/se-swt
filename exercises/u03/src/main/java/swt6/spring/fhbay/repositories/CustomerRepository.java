package swt6.spring.fhbay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.fhbay.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
