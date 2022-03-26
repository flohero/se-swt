package swt6.spring.services;


import swt6.spring.model.Article;
import swt6.spring.model.Category;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article save(Article article);

    Optional<Article> findById(Long id);

    Category createCategory(Category category);

    List<Category> findAllCategories();

    List<Article> findAuctionedArticles();
}
