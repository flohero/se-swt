package swt6.spring.services;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.model.Article;
import swt6.spring.model.BiddingState;
import swt6.spring.model.Category;
import swt6.spring.repositories.ArticleRepository;
import swt6.spring.repositories.CategoryRepository;
import swt6.spring.repositories.CustomerRepository;
import swt6.spring.services.exceptions.ArticleNotFoundException;
import swt6.spring.services.exceptions.CustomerNotFoundException;
import swt6.spring.services.exceptions.InvalidArticleException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
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

    @Override
    public Article createArticle(Article article) {
        Category category = article.getCategory();
        if (article.getStartDate().isAfter(article.getEndDate())
                || article.getEndDate().isBefore(LocalDate.now())
                || article.getStartPrice() < 0
                || article.getState() == BiddingState.EXPIRED
                || article.getName().isEmpty()
                || category == null) {
            throw new InvalidArticleException();
        }
        if (!customerRepository.existsById(article.getSeller().getId())) {
            throw new CustomerNotFoundException();
        }
        article.setCategory(
                categoryRepository.findCategoryByName(category.getName())
                        .orElseGet(() -> categoryRepository.save(category))
        );
        return articleRepository.save(article);
    }
}
