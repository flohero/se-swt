package swt6.spring.dtos;

import java.time.LocalDate;

public class BidDto extends BaseDto {
    private Double amount;

    private LocalDate date;

    private ArticleDto article;

    private CustomerDto customer;

    public BidDto() {
    }

    public BidDto(Double amount, LocalDate date, ArticleDto article, CustomerDto customer) {
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

    public ArticleDto getArticle() {
        return article;
    }

    public void setArticle(ArticleDto article) {
        this.article = article;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return amount + "€\n" +
               "Placed on " + date.toString() + "\n" +
                "By " + customer.getFirstname() + " " + customer.getLastname() + "\n";
    }
}
