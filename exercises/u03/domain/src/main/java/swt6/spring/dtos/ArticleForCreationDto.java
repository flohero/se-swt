package swt6.spring.dtos;

import swt6.spring.model.BiddingState;

import java.time.LocalDate;

public class ArticleForCreationDto {
    private String name;

    private String description;

    private double startPrice;

    private LocalDate startDate;

    private LocalDate endDate;

    private CustomerDto seller;

    private BiddingState state;

    private CategoryDto category;

    public ArticleForCreationDto() {
    }

    public ArticleForCreationDto(String name, String description, double startPrice, LocalDate startDate, LocalDate endDate, CustomerDto seller, BiddingState state, CategoryDto category) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.seller = seller;
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
