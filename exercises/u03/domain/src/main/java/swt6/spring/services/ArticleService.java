package swt6.spring.services;


import swt6.spring.model.Article;
import swt6.spring.model.BiddingState;
import swt6.spring.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ArticleService {
    Article save(Article article);

    Article findById(Long id);

    Category createCategory(Category category);

    List<Category> findAllCategories();

    List<Article> findAuctionedArticles();

    List<Article> findArticles(BiddingState state);
}
