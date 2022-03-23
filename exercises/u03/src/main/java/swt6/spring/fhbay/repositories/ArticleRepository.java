package swt6.spring.fhbay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.fhbay.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
