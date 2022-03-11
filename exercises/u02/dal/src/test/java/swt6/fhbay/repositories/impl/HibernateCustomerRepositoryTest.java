package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.Address;
import swt6.fhbay.domain.BankAccount;
import swt6.fhbay.domain.Customer;

import static org.junit.jupiter.api.Assertions.*;

class HibernateCustomerRepositoryTest extends TestBase {

    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new HibernateCustomerRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(Customer.class, persistentClass);
    }

    @Test
    @DisplayName("Find Customer by Id")
    void testFindCustomerById() {
        // given
        var repo = new HibernateCustomerRepository(em);
        var customer = repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));

        // when
        var res = repo.findById(customer.getId());

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Find by ID for non existent Customer returns null")
    void testFindByIdForNonExistentCustomer_returnsNull() {
        // given
        var repo = new HibernateCustomerRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertNull(res);
    }

    @Test
    @DisplayName("Find all Customers")
    void findAllCustomers() {
        // given
        var repo = new HibernateCustomerRepository(em);
        repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));
        repo.save(new Customer("max2", "muster", "max2@muster.com", new Address(), new Address()));

        // when
        var res = repo.findAll();

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count all Customers")
    void testCountAllCustomers() {
        // given
        var repo = new HibernateCustomerRepository(em);
        repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));
        repo.save(new Customer("max", "muster", "max2@muster.com", new Address(), new Address()));

        // when
        var res = repo.countAll();

        // then
        assertEquals(2, res);
    }

    @Test
    @DisplayName("Insert Customer successfully")
    void testInsertCustomerSuccessfully() {
        // given
        var repo = new HibernateCustomerRepository(em);

        // when
        var res = repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Remove Customer")
    void testRemoveCustomer() {
        // given
        var repo = new HibernateCustomerRepository(em);
        var customer = repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));

        // when
        repo.remove(customer);

        //then
        var res = repo.findById(customer.getId());
        assertNull(res);
    }

    @Test
    @DisplayName("Remove Customer by ID")
    void testRemoveCustomerId() {
        // given
        var repo = new HibernateCustomerRepository(em);
        var customer = repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));

        // when
        var res = repo.removeById(customer.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent Customer by ID returns zero")
    void testRemoveNonExistentCustomerId_returnsZero() {
        // given
        var repo = new HibernateCustomerRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }

    @Test
    @DisplayName("Find Customers by lastname")
    void testFindCustomersByLastname() {
        // given
        var repo = new HibernateCustomerRepository(em);
        repo.save(new Customer("max", "muster", "max@muster.com", new Address(), new Address()));
        repo.save(new Customer("franz", "muster", "franz@muster.com", new Address(), new Address()));
        repo.save(new Customer("Dr", "Acula", "dr@acula.com", new Address(), new Address()));

        // when
        var res = repo.findByLastname("muster");

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Add PaymentMethod to Customer")
    void testAddPaymentMethodToCustomer() {
        // given
        var repo = new HibernateCustomerRepository(em);
        var customer = new Customer("max", "muster", "max@muster.com", new Address(), new Address());

        // when
        customer.addPaymentMethod(new BankAccount("max", "muster", "IBAN"));
        var res = repo.save(customer);

        // then
        assertAll(
                () -> assertNotNull(res.getId()),
                () -> assertEquals(1, res.getPaymentMethods().size())
        );
    }

    @Test
    @DisplayName("Remove PaymentMethod from Customer")
    void testRemovePaymentMethodFromCustomer() {
        // given
        var repo = new HibernateCustomerRepository(em);
        var bankAccountRepository = new HibernateBankAccountRepository(em);
        var customer = new Customer("max", "muster", "max@muster.com", new Address(), new Address());
        var paymentMethod = bankAccountRepository.save(new BankAccount("max", "muster", "IBAN"));
        customer.addPaymentMethod(paymentMethod);
        repo.save(customer);

        // when
        customer.removePaymentMethod(paymentMethod);
        var res = repo.save(customer);

        // then
        assertEquals(0, res.getPaymentMethods().size());
    }

}