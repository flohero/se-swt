package swt6.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.spring.model.*;
import swt6.spring.services.ArticleService;
import swt6.spring.services.BidService;
import swt6.spring.services.CustomerService;

import java.time.LocalDate;
import java.util.List;


@Component
@Profile("dev")
@Order(-1)
public class DatabaseInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    private final CustomerService customerService;
    private final ArticleService articleService;
    private final BidService bidService;

    public DatabaseInitializer(CustomerService customerService, ArticleService articleService, BidService bidService) {
        this.customerService = customerService;
        this.articleService = articleService;
        this.bidService = bidService;
    }

    @Override
    public void run(String... args) {
        Customer c1 = new Customer("Max", "Muster", "max@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße"));
        Customer c2 = new Customer("Susi", "Muster", "sus@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße"));
        Customer c3 = new Customer("Franz", "Muster", "franz@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße"));
        Customer c4 = new Customer("Anna", "Muster", "anna@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße"));
        List<Customer> customers = customerService.saveMany(c1, c2, c3, c4);
        c1 = customers.get(0);
        c2 = customers.get(1);
        c3 = customers.get(2);
        c4 = customers.get(3);

        Article a1 = articleService.save(new Article("Fish", "A water animal", 10, 0, LocalDate.now().minusDays(4), LocalDate.now().minusDays(2), c1, null, null, BiddingState.FOR_SALE, new Category("Animals")));
        Article a2 = articleService.save(new Article("Dear", "Not a water animal", 1000, 0, LocalDate.now(), LocalDate.now().plusDays(2), c1, null, null, BiddingState.FOR_SALE, a1.getCategory()));
        Bid bid1 = bidService.save(new Bid(100.0, LocalDate.now().minusDays(3), a1, c2));
        Bid bid2 = bidService.save(new Bid(110.0, LocalDate.now().minusDays(3), a1, c3));

        Bid bid3 = bidService.save(new Bid(1200.0, LocalDate.now(), a2, c3));
    }
}
