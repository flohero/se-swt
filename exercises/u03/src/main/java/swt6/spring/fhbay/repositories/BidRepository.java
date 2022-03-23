package swt6.spring.fhbay.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.fhbay.domain.Article;
import swt6.spring.fhbay.domain.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    Page<Bid> findByArticleOrderByAmountDescDateDesc(Article article, Pageable pageable);

}
