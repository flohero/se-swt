package swt6.spring.services;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.model.Article;
import swt6.spring.model.BiddingState;
import swt6.spring.model.Category;
import swt6.spring.repositories.ArticleRepository;
import swt6.spring.repositories.CategoryRepository;
import swt6.spring.services.exceptions.ArticleNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Article save(@NonNull Article article) {
        Category category = Objects.requireNonNull(article.getCategory());
        article.setCategory(categoryRepository.save(category));
        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Article findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
        return articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        Category parent = category.getParentCategory();
        Optional<Category> optionalParent = categoryRepository.findCategoryByName(parent.getName());
        category.setParentCategory(optionalParent.orElseGet(() -> createCategory(parent)));
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findAuctionedArticles() {
        return articleRepository.findAllByBuyerAndEndDateBeforeAndState(
                null,
                LocalDate.now(),
                BiddingState.FOR_SALE
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> findArticles(BiddingState state) {
        return articleRepository.findByState(state);
    }
}
