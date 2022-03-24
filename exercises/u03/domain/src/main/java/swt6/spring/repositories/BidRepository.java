package swt6.spring.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.model.Article;
import swt6.spring.model.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Page<Bid> findByArticleOrderByAmountDescDateDesc(Article article, Pageable pageable);

}
