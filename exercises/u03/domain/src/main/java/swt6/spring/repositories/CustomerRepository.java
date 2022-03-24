package swt6.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
