package swt6.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.model.Article;
import swt6.spring.model.BiddingState;
import swt6.spring.model.Customer;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByBuyerAndEndDateBeforeAndState(Customer buyer, LocalDate date, BiddingState state);
}
