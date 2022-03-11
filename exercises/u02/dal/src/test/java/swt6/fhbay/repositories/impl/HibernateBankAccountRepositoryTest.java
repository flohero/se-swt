package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.BankAccount;

import static org.junit.jupiter.api.Assertions.*;

class HibernateBankAccountRepositoryTest extends TestBase {

    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new HibernateBankAccountRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(BankAccount.class, persistentClass);
    }

    @Test
    @DisplayName("Find BankAccount by Id")
    void testFindBankAccountById() {
        // given
        var repo = new HibernateBankAccountRepository(em);
        var bankAccount = repo.save(new BankAccount(null, "max", "muster", "IBAN"));

        // when
        var res = repo.findById(bankAccount.getId());

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Find by ID for non existent BankAccount returns null")
    void testFindByIdForNonExistentBankAccount_returnsNull() {
        // given
        var repo = new HibernateBankAccountRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertNull(res);
    }

    @Test
    @DisplayName("Find all BankAccounts")
    void findAllBankAccounts() {
        // given
        var repo = new HibernateBankAccountRepository(em);
        repo.save(new BankAccount(null, "max", "muster", "IBAN"));
        repo.save(new BankAccount(null, "max", "muster", "IBAN2"));

        // when
        var res = repo.findAll();

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count all BankAccounts")
    void testCountAllBankAccounts() {
        // given
        var repo = new HibernateBankAccountRepository(em);
        repo.save(new BankAccount(null, "max", "muster", "IBAN"));
        repo.save(new BankAccount(null, "max", "muster", "IBAN2"));

        // when
        var res = repo.countAll();

        // then
        assertEquals(2, res);
    }

    @Test
    @DisplayName("Insert BankAccount successfully")
    void testInsertBankAccountSuccessfully() {
        // given
        var repo = new HibernateBankAccountRepository(em);

        // when
        var res = repo.save(new BankAccount(null, "max", "muster", "IBAN"));

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Remove BankAccount")
    void testRemoveAccount() {
        // given
        var repo = new HibernateBankAccountRepository(em);
        var bankAccount = repo.save(new BankAccount(null, "max", "muster", "IBAN"));

        // when
        repo.remove(bankAccount);

        //then
        var res = repo.findById(bankAccount.getId());
        assertNull(res);
    }

    @Test
    @DisplayName("Remove BankAccount by ID")
    void testRemoveBankAccountId() {
        // given
        var repo = new HibernateBankAccountRepository(em);
        var bankAccount = repo.save(new BankAccount(null, "max", "muster", "IBAN"));

        // when
        var res = repo.removeById(bankAccount.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent category by ID returns zero")
    void testRemoveNonExistentBankAccountId_returnsZero() {
        // given
        var repo = new HibernateBankAccountRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }
}