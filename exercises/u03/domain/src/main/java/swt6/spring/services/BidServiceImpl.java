package swt6.spring.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.model.Article;
import swt6.spring.model.Bid;
import swt6.spring.model.BiddingState;
import swt6.spring.model.Customer;
import swt6.spring.repositories.ArticleRepository;
import swt6.spring.repositories.BidRepository;
import swt6.spring.repositories.CustomerRepository;
import swt6.spring.services.exceptions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final ArticleRepository articleRepository;
    private final CustomerRepository customerRepository;
    private final Logger logger = LoggerFactory.getLogger(BidServiceImpl.class);

    public BidServiceImpl(BidRepository bidRepository, ArticleRepository articleRepository, CustomerRepository customerRepository) {
        this.bidRepository = bidRepository;
        this.articleRepository = articleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bid> findSecondHighestBidForArticle(Article article) {
        Page<Bid> bidPage = bidRepository.findByArticleOrderByAmountDescDateAsc(article, PageRequest.of(1, 1));
        return bidPage.stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bid> findHighestBidForArticle(Article article) {
        Page<Bid> bidPage = bidRepository.findByArticleOrderByAmountDescDateAsc(article, PageRequest.ofSize(1));
        return bidPage.stream().findFirst();
    }

    @Override
    @Transactional
    public Bid save(Bid bid) {
        if (bid.getArticle().getSeller().equals(bid.getCustomer())) {
            throw new SellerAndBuyerEqualException();
        }
        return bidRepository.save(bid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bid> findBidsForArticleId(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ArticleNotFoundException();
        }
        return bidRepository.findByArticle_Id(id);
    }

    @Override
    @Transactional
    public Bid placeBidForArticle(Long id, Bid bid) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        if (!article.getState().equals(BiddingState.FOR_SALE)
                || article.getEndDate().isBefore(LocalDate.now())) {
            throw new ArticleNotForSaleException();
        }

        Customer customer = bid.getCustomer();
        if (customer == null || !customerRepository.existsById(customer.getId())) {
            throw new CustomerNotFoundException();
        }

        if (customer.getId().equals(article.getSeller().getId())) {
            throw new SellerAndBuyerEqualException();
        }

        if (bidRepository.findByCustomerAndArticle(customer, article).isPresent()) {
            throw new DuplicatedBidException();
        }

        if(bid.getAmount() < article.getStartPrice()) {
            throw new InvalidBidException();
        }

        bid.setArticle(article);
        return bidRepository.save(bid);
    }

    @Override
    public Bid findWinningBidForArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new ArticleNotFoundException();
        }
        if(article.get().getState() != BiddingState.SOLD) {
            throw new ArticleNotAuctionedException();
        }
        Optional<Bid> bid = findHighestBidForArticle(article.get());
        if(bid.isEmpty()) {
            throw new NoBidPresentException();
        }
        return bid.get();
    }
}
