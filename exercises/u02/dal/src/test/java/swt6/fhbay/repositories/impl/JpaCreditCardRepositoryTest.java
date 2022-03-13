package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.Address;
import swt6.fhbay.domain.CreditCard;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JpaCreditCardRepositoryTest extends TestBase {
    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new JpaCreditCardRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(CreditCard.class, persistentClass);
    }

    @Test
    @DisplayName("Find CreditCard by Id")
    void testFindCreditCardById() {
        // given
        var repo = new JpaCreditCardRepository(em);
        var creditCard = repo.save(new CreditCard("max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));

        // when
        var res = repo.findById(creditCard.getId());

        // then
        assertTrue(res.isPresent());
    }

    @Test
    @DisplayName("Find by ID for non existent CreditCard returns null")
    void testFindByIdForNonExistentCreditCard_returnsNull() {
        // given
        var repo = new JpaCreditCardRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Find all CreditCards")
    void findAllCreditCards() {
        // given
        var repo = new JpaCreditCardRepository(em);
        repo.save(new CreditCard( "max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));
        repo.save(new CreditCard( "franz", "muster", "Visa", "cool number2", new Address(), LocalDate.now().plusYears(1)));

        // when
        var res = repo.findAll();

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count all CreditCards")
    void testCountAllCreditCards() {
        // given
        var repo = new JpaCreditCardRepository(em);
        repo.save(new CreditCard( "max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));
        repo.save(new CreditCard( "franz", "muster", "Visa", "cool number2", new Address(), LocalDate.now().plusYears(1)));

        // when
        var res = repo.countAll();

        // then
        assertEquals(2, res);
    }

    @Test
    @DisplayName("Insert CreditCard successfully")
    void testInsertCreditCardSuccessfully() {
        // given
        var repo = new JpaCreditCardRepository(em);

        // when
        var res = repo.save(new CreditCard( "max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Remove CreditCard")
    void testRemoveCreditCard() {
        // given
        var repo = new JpaCreditCardRepository(em);
        var creditCard = repo.save(new CreditCard( "max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));

        // when
        repo.remove(creditCard);

        //then
        var res = repo.findById(creditCard.getId());
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Remove CreditCard by ID")
    void testRemoveCreditCardId() {
        // given
        var repo = new JpaCreditCardRepository(em);
        var creditCard = repo.save(new CreditCard( "max", "muster", "Visa", "cool number", new Address(), LocalDate.now().plusYears(1)));

        // when
        var res = repo.removeById(creditCard.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent CreditCard by ID returns zero")
    void testRemoveNonExistentCreditCardId_returnsZero() {
        // given
        var repo = new JpaCreditCardRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }
}