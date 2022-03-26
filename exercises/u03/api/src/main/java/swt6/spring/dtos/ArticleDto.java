package swt6.spring.dtos;

import swt6.spring.model.BiddingState;

import java.time.LocalDate;

public class ArticleDto extends BaseDto {

    private String name;

    private String description;

    private double startPrice;

    private double endPrice;

    private LocalDate startDate;

    private LocalDate endDate;

    private CustomerDto seller;

    private CustomerDto buyer;

    private CustomerDto secondHighestBidder;

    private BiddingState state;

    private CategoryDto category;

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

    public CustomerDto getSeller() {
        return seller;
    }

    public void setSeller(CustomerDto seller) {
        this.seller = seller;
    }

    public CustomerDto getBuyer() {
        return buyer;
    }

    public void setBuyer(CustomerDto buyer) {
        this.buyer = buyer;
    }

    public CustomerDto getSecondHighestBidder() {
        return secondHighestBidder;
    }

    public void setSecondHighestBidder(CustomerDto secondHighestBidder) {
        this.secondHighestBidder = secondHighestBidder;
    }

    public BiddingState getState() {
        return state;
    }

    public void setState(BiddingState state) {
        this.state = state;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
