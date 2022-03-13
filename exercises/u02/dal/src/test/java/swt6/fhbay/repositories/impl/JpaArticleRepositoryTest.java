package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JpaArticleRepositoryTest extends TestBase {
    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new JpaArticleRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(Article.class, persistentClass);
    }

    @Test
    @DisplayName("Find Article by Id")
    void testFindArticleById() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        var customerRepo = new JpaCustomerRepository(em);
        seller = customerRepo.save(seller);
        var article = repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.findById(article.getId());

        // then
        assertTrue(res.isPresent());
    }

    @Test
    @DisplayName("Find by ID for non existent Article returns null")
    void testFindByIdForNonExistentArticle_returnsNull() {
        // given
        var repo = new JpaArticleRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Find all Article")
    void findAllArticles() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        var customerRepo = new JpaCustomerRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        seller = customerRepo.save(seller);

        repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.findAll();

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count all Articles")
    void testCountAllArticles() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        var customerRepo = new JpaCustomerRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        seller = customerRepo.save(seller);

        repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.countAll();

        // then
        assertEquals(2, res);
    }

    @Test
    @DisplayName("Insert Article successfully")
    void testInsertArticleSuccessfully() {
        // given
        var repo = new JpaArticleRepository(em);
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        // when
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        var customerRepo = new JpaCustomerRepository(em);
        seller = customerRepo.save(seller);
        var res = repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Remove Article")
    void testRemoveArticle() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        var customerRepo = new JpaCustomerRepository(em);
        seller = customerRepo.save(seller);
        var article = repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        repo.remove(article);

        //then
        var res = repo.findById(article.getId());
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Remove Article by ID")
    void testRemoveArticleId() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        var customerRepo = new JpaCustomerRepository(em);
        seller = customerRepo.save(seller);
        var article = repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.removeById(article.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent Article by ID returns zero")
    void testRemoveNonExistentArticleId_returnsZero() {
        // given
        var repo = new JpaArticleRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }

    @Test
    @DisplayName("Find were Name contains string returns correct article")
    void testFindWereNameContainsString_returnsCorrectArticle() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        var customerRepo = new JpaCustomerRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        seller = customerRepo.save(seller);

        var flamingo = repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        repo.save(new Article("Sparrow", "Pirate Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.findWereNameOrDescriptionContains("lamingo").get(0);

        // then
        assertEquals(flamingo.getId(), res.getId());
    }

    @Test
    @DisplayName("Find were Description contains string returns correct articles")
    void testFindWereDescriptionContainsString_returnsCorrectArticle() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));

        var repo = new JpaArticleRepository(em);
        var customerRepo = new JpaCustomerRepository(em);
        Customer seller = new Customer("Max", "Muster", "max@muster.com", new Address(), new Address());
        seller = customerRepo.save(seller);

        repo.save(new Article("Flamingo", "Pink Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        repo.save(new Article("Sparrow", "Pirate Bird",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        repo.save(new Article("Python", "Comedy Group",
                100, 0,
                LocalDate.now(), LocalDate.now().plusDays(1),
                seller,
                null, null,
                BiddingState.FOR_SALE, cat));

        // when
        var res = repo.findWereNameOrDescriptionContains("Bird");

        // then
        assertEquals(2, res.size());
    }
}