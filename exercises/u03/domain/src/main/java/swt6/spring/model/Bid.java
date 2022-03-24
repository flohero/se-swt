package swt6.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Bid extends EntityBase {
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    private Article article;

    @ManyToOne(optional = false)
    private Customer customer;

    public Bid() {

    }

    public Bid(Double amount, LocalDate date, Article article, Customer customer) {
        this.amount = amount;
        this.date = date;
        this.article = article;
        this.customer = customer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "amount=" + amount +
                ", date=" + date +
                ", article=" + article +
                ", customer=" + customer +
                '}';
    }
}
