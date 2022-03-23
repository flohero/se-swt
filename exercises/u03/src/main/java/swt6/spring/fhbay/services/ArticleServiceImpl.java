package swt6.spring.fhbay.services;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import swt6.spring.fhbay.domain.Article;
import swt6.spring.fhbay.domain.Category;
import swt6.spring.fhbay.repositories.ArticleRepository;
import swt6.spring.fhbay.repositories.CategoryRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Article save(@NonNull Article article) {
        Category category = Objects.requireNonNull(article.getCategory());
        article.setCategory(categoryRepository.save(category));
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        if(id == null) {
            return Optional.empty();
        }
        return articleRepository.findById(id);
    }
}
