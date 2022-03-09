package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ArticleCategory extends EntityBase {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private ArticleCategory articleCategory;

    public ArticleCategory() {}

    public ArticleCategory(String name) {
        this.name = name;
    }

    public ArticleCategory(String name, ArticleCategory articleCategory) {
        this.name = name;
        this.articleCategory = articleCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }
}
