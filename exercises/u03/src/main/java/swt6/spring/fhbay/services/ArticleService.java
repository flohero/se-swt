package swt6.spring.fhbay.services;

import swt6.spring.fhbay.domain.Article;

import java.util.Optional;

public interface ArticleService {
    Article save(Article article);

    Optional<Article> findById(Long id);
}
