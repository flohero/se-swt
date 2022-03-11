package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Article extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double startPrice;

    private double endPrice;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(optional = false)
    private Customer seller;

    @ManyToOne
    private Customer buyer;

    @ManyToOne
    private Customer secondHighestBidder;

    @Column(nullable = false)
    private BiddingState state;

    @ManyToOne
    private Category category;

    public Article() {
    }

    public Article(String name, String description, double startPrice, double endPrice, LocalDate startDate, LocalDate endDate, BiddingState state) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
    }

    public Article(String name, String description,
                   double startPrice, double endPrice,
                   LocalDate startDate, LocalDate endDate,
                   Customer seller, Customer buyer,
                   Customer secondHighestBidder,
                   BiddingState state, Category category) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seller = seller;
        this.buyer = buyer;
        this.secondHighestBidder = secondHighestBidder;
        this.state = state;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    public Customer getSecondHighestBidder() {
        return secondHighestBidder;
    }

    public void setSecondHighestBidder(Customer secondHighestBidder) {
        this.secondHighestBidder = secondHighestBidder;
    }

    public BiddingState getState() {
        return state;
    }

    public void setState(BiddingState state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
