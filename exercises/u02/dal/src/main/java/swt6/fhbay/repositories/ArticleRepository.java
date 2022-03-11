package swt6.fhbay.repositories;

import swt6.fhbay.domain.Article;

import java.util.List;

public interface ArticleRepository extends Repository<Article, Long> {

    List<Article> findWereNameOrDescriptionContains(String text);
}
