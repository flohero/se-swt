package swt6.spring.fhbay.services;

import swt6.spring.fhbay.domain.Article;
import swt6.spring.fhbay.domain.Bid;

import java.util.Optional;

public interface BidService {

    Optional<Bid> findSecondHighestBidForArticle(Article article);

    Bid save(Bid bid);

}
