package swt6.spring.fhbay.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.fhbay.domain.*;
import swt6.spring.fhbay.dtos.CustomerDto;
import swt6.spring.fhbay.services.ArticleService;
import swt6.spring.fhbay.services.BidService;
import swt6.spring.fhbay.services.CustomerService;

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
    @Transactional
    public void run(String... args) {
        Customer c1 = new Customer("Max", "Muster", "max@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße") );
        Customer c2 = new Customer("Susi", "Muster", "sus@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße") );
        Customer c3 = new Customer("Franz", "Muster", "franz@muster.com", new Address("Austria", "1230", "Vienna", "Musterstraße") );
        List<CustomerDto> customers = customerService.saveMany(c1, c2, c3);
        CustomerDto cDto1 = customers.get(0);
        CustomerDto cDto2 = customers.get(1);
        CustomerDto cDto3 = customers.get(2);

        //Article a = articleService.save(new Article("Fish", "A water animal", 10, 0, LocalDate.now(), LocalDate.now().plusDays(2), c1, null, null, BiddingState.FOR_SALE, new Category("Animals")));
        //Bid bid1 = bidService.save(new Bid(100.0, LocalDate.now(), a, c2));
        //Bid bid2 = bidService.save(new Bid(110.0, LocalDate.now(), a, c3));
    }
}
