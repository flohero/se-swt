package swt6.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import swt6.spring.model.Article;
import swt6.spring.model.Bid;
import swt6.spring.model.BiddingState;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class VickreyAuctionService implements AuctionService {

    private final BidService bidService;
    private final ArticleService articleService;
    private final Logger logger = LoggerFactory.getLogger(VickreyAuctionService.class);

    public VickreyAuctionService(BidService bidService, ArticleService articleService) {
        this.bidService = bidService;
        this.articleService = articleService;
    }


    @Scheduled(fixedRate = 10000)
    @Override
    public void updateBuyersOfArticles() {
        logger.info("Update Buyer for Article");
        List<Article> articles = articleService.findAuctionedArticles();
        for (Article article : articles) {
            updateArticleBuyer(article);
        }
    }

    private void updateArticleBuyer(Article article) {
        Optional<Bid> highestBid = bidService.findHighestBidForArticle(article);
        Optional<Bid> secondHighestBid = bidService.findSecondHighestBidForArticle(article);
        if (highestBid.isEmpty() || secondHighestBid.isEmpty()) {
            article.setState(BiddingState.NOT_FOR_SALE);
        } else {
            article.setSecondHighestBidder(secondHighestBid.get().getCustomer());
            article.setBuyer(highestBid.get().getCustomer());
            article.setEndDate(LocalDate.now());
            article.setState(BiddingState.SOLD);
        }
        articleService.save(article);
    }
}
