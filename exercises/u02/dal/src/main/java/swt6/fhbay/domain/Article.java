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

    @ManyToOne
    @Column(nullable = false)
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
}
