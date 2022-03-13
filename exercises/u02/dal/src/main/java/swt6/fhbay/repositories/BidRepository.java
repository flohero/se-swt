package swt6.fhbay.repositories;

import swt6.fhbay.domain.Article;
import swt6.fhbay.domain.Bid;

import java.util.Optional;

public interface BidRepository extends Repository<Bid, Long> {

    Optional<Bid> findSecondHighestBidForArticle(Article article);

    Optional<Bid> findHighestBidForArticle(Article article);
}
