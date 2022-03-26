package swt6.spring.services;


import swt6.spring.model.Article;
import swt6.spring.model.Bid;

import java.util.List;
import java.util.Optional;

public interface BidService {

    Optional<Bid> findSecondHighestBidForArticle(Article article);

    Bid save(Bid bid);

    List<Bid> findBidsForArticleId(Long id);

    Bid placeBidForArticle(Long id, Bid bid);
}
