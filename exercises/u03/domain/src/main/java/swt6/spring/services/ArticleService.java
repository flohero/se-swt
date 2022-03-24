package swt6.spring.services;


import swt6.spring.model.Article;

import java.util.Optional;

public interface ArticleService {
    Article save(Article article);

    Optional<Article> findById(Long id);
}
