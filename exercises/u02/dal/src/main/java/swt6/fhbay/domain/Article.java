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
    private ArticleCategory category;

}
