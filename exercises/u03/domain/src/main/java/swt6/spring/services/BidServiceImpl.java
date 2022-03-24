package swt6.spring.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import swt6.spring.model.Article;
import swt6.spring.model.Bid;
import swt6.spring.repositories.BidRepository;

import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public Optional<Bid> findSecondHighestBidForArticle(Article article) {
        Page<Bid> bidPage = bidRepository.findByArticleOrderByAmountDescDateDesc(article, PageRequest.of(1, 1));
        return bidPage.stream().findFirst();
    }

    public Bid save(Bid bid) {
        if (bid.getArticle().getSeller().equals(bid.getCustomer())) {
            throw new IllegalStateException("Seller and buyer can't be the same customer");
        }
        return bidRepository.save(bid);
    }
}
