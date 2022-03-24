package swt6.spring.services;


import swt6.spring.model.Article;
import swt6.spring.model.Bid;

import java.util.Optional;

public interface BidService {

    Optional<Bid> findSecondHighestBidForArticle(Article article);

    Bid save(Bid bid);

}
