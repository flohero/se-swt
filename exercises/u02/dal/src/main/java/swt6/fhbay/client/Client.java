package swt6.fhbay.client;

import swt6.fhbay.domain.*;
import swt6.fhbay.repositories.RepositoryFactory;
import swt6.fhbay.repositories.impl.JpaArticleRepository;
import swt6.fhbay.repositories.impl.JpaCategoryRepository;
import swt6.fhbay.repositories.impl.JpaCustomerRepository;
import swt6.fhbay.util.JpaUtil;

import java.time.LocalDate;

public class Client {
    public static void main(String[] args) {
        System.out.println("Start Auction");
        JpaUtil.getEntityManagerFactory();
        var em = JpaUtil.getTransactedEntityManager();

        System.out.println("Creating Category");
        var catRepo = RepositoryFactory.getCategoryRepository(em);
        var parentCat = catRepo.save(new Category("Animals"));
        var cat = catRepo.save(new Category("Illegal Animals", parentCat));
        System.out.println("Inserted category: " + parentCat);

        System.out.println("Inserting customers");
        var customerRepo = RepositoryFactory.getCustomerRepository(em);
        var customer1 = customerRepo.save(new Customer("max", "muster", "max@muster", new Address("austria", "1230", "Vienna", "musterstreet"), new Address("austria", "1230", "Vienna", "musterstreet")));
        var customer2 = customerRepo.save(new Customer("franz", "muster", "franz@muster", new Address("austria", "1230", "Vienna", "otherstreet"), new Address("austria", "1230", "Vienna", "otherstreet")));
        var customer3 = customerRepo.save(new Customer("susi", "muster", "susi@muster", new Address("austria", "1230", "Vienna", "beststreet"), new Address("austria", "1230", "Vienna", "beststreet")));

        System.out.println("Adding Payment Methods");
        var bankAccountRepo = RepositoryFactory.getBankAccountRepository(em);
        var bankAcc = bankAccountRepo.save(new BankAccount("franz", "muster", "IBAN"));
        customer2.addPaymentMethod(bankAcc);
        customerRepo.save(customer2);
        var creditCardRepo = RepositoryFactory.getCreditCardRepository(em);
        var creditCard = creditCardRepo.save(new CreditCard("susi", "muster", "VISA", "number", new Address("austria", "1230", "Vienna", "beststreet"), LocalDate.now().plusYears(1)));
        customer3.addPaymentMethod(creditCard);
        customerRepo.save(customer3);

        System.out.println("Inserting article sold by customer 1");
        var articleRepo = RepositoryFactory.getArticleRepository(em);
        var article = articleRepo.save(new Article("Dolphin", "A fish thats not a fish", 100.0, 0.0, LocalDate.now(), LocalDate.now().plusDays(1), customer1, null, null, BiddingState.FOR_SALE, cat));

        System.out.println("Inserting bids from customer 2 and 3");
        var bidRepo = RepositoryFactory.getBidRepository(em);

        var bid1 = bidRepo.save(new Bid(110.0, LocalDate.now(), article, customer2));
        var bid2 = bidRepo.save(new Bid(10000.0, LocalDate.now(), article, customer3));

        System.out.println("Getting the second highest bidder");
        var secondHighestBid = bidRepo.findSecondHighestBidForArticle(article);
        System.out.println(secondHighestBid.orElseThrow());

        System.out.println("Getting the winning bid");
        var winningBid = bidRepo.findHighestBidForArticle(article);
        System.out.println(winningBid.orElseThrow());

        System.out.println("Updating Article");
        article.setBuyer(winningBid.get().getCustomer());
        article.setSecondHighestBidder(secondHighestBid.get().getCustomer());
        articleRepo.save(article);

        System.out.println(article);

        JpaUtil.commit();
        JpaUtil.closeEntityManagerFactory();
        System.out.println("Committing changes");
    }
}
