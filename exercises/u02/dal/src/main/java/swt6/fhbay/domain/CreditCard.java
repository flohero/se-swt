package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class CreditCard extends PaymentMethod {
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @ManyToOne
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    private LocalDate expiry;
}
