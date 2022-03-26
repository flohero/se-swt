package swt6.spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.model.Article;
import swt6.spring.model.Bid;
import swt6.spring.model.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Page<Bid> findByArticleOrderByAmountDescDateDesc(Article article, Pageable pageable);

    List<Bid> findByArticle_Id(Long id);

    Optional<Bid> findByCustomerAndArticle(Customer customer, Article article);

}
