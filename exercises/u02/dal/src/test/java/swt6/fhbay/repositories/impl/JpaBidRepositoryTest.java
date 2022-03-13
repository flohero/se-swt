package swt6.fhbay.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swt6.fhbay.domain.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JpaBidRepositoryTest extends TestBase {

    @Test
    @DisplayName("Repository returns correct domain class")
    void testPersistenceClassReturnsIsCorrectDomainClass() {
        // given
        var repo = new JpaBidRepository(null);
        // when
        var persistentClass = repo.getPersistentClass();
        // then
        assertEquals(Bid.class, persistentClass);
    }

    @Test
    @DisplayName("Find Bid by Id")
    void testFindBidById() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        var bid = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        var res = repo.findById(bid.getId());

        // then
        assertTrue(res.isPresent());
    }

    @Test
    @DisplayName("Find by ID for non existent Bid returns null")
    void testFindByIdForNonExistentBid_returnsNull() {
        // given
        var repo = new JpaBidRepository(em);

        // when
        var res = repo.findById(1000L);

        //then
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Find all Bids")
    void findAllBids() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));
        var bidder2 = customerRepo.save(new Customer("bidder2", "muster", "bidder2@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        repo.save(new Bid(100.0, LocalDate.now(), article, bidder));
        repo.save(new Bid(1000.0, LocalDate.now(), article, bidder2));

        // when
        var res = repo.findAll();

        // then
        assertEquals(2, res.size());
    }

    @Test
    @DisplayName("Count all Bids")
    void testCountAllBids() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));
        var bidder2 = customerRepo.save(new Customer("bidder2", "muster", "bidder2@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        repo.save(new Bid(100.0, LocalDate.now(), article, bidder));
        repo.save(new Bid(1000.0, LocalDate.now(), article, bidder2));

        // when
        var res = repo.countAll();

        // then
        assertEquals(2, res);
    }

    @Test
    @DisplayName("Insert Bid successfully")
    void testInsertBidSuccessfully() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        var res = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));


        // then
        assertNotNull(res);
    }

    @Test
    @DisplayName("Remove Bid")
    void testRemoveBid() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        var bid = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        repo.remove(bid);

        //then
        var res = repo.findById(bid.getId());
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("Remove Bid by ID")
    void testRemoveBidId() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        var bid = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        var res = repo.removeById(bid.getId());

        //then
        assertEquals(1, res);
    }

    @Test
    @DisplayName("Remove non existent Bid by ID returns zero")
    void testRemoveNonExistentBidId_returnsZero() {
        // given
        var repo = new JpaBidRepository(em);

        // when
        var res = repo.removeById(1000L);

        //then
        assertEquals(0, res);
    }

    @Test
    @DisplayName("FindSecondHighestBidForArticle returns correct bid")
    void testFindSecondHighestBidForArticle_returnsCorrectBid() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));
        var secondBidder = customerRepo.save(new Customer("secondBidder", "muster", "secondBidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        var bid1 = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));
        var bid2 = repo.save(new Bid(90.0, LocalDate.now(), article, secondBidder));

        // when
        var res = repo.findSecondHighestBidForArticle(article);

        // then
        assertEquals(bid2.getId(), res.get().getId());
    }

    @Test
    @DisplayName("FindSecondHighestBidForArticle returns null if there is no second bid")
    void testFindSecondHighestBidForArticle_returnsNullIfThereIsNoSecondBid() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        var res = repo.findSecondHighestBidForArticle(article);

        // then
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("findHighestBidForArticle returns correct bid")
    void testFindHighestBidForArticle_returnsCorrectBid() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));
        var bidder = customerRepo.save(new Customer("bidder", "muster", "bidder@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);
        var bid = repo.save(new Bid(100.0, LocalDate.now(), article, bidder));

        // when
        var res = repo.findHighestBidForArticle(article);

        // then
        assertEquals(bid.getId(), res.get().getId());
    }

    @Test
    @DisplayName("findHighestBidForArticle returns null if there is no highest bid")
    void testFindHighestBidForArticle_returnsNullIfThereIsHighestBid() {
        // given
        var categoryRepo = new JpaCategoryRepository(em);
        var cat = categoryRepo.save(new Category("Stuff"));
        var customerRepo = new JpaCustomerRepository(em);
        var seller = customerRepo.save(new Customer("seller", "muster", "seller@muster.com", new Address(), new Address()));

        var articleRepo = new JpaArticleRepository(em);
        var article = articleRepo.save(new Article("good stuff", "The best stuff", 50.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), seller, null, null, BiddingState.FOR_SALE, cat));

        var repo = new JpaBidRepository(em);

        // when
        var res = repo.findHighestBidForArticle(article);

        // then
        assertTrue(res.isEmpty());
    }
}