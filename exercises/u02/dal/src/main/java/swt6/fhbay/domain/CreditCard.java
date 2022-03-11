package swt6.fhbay.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CreditCard extends PaymentMethod {
    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Embedded
    @AttributeOverride(name = "country", column = @Column(name = "address_country"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "address_zipCode"))
    @AttributeOverride(name = "city", column = @Column(name = "address_city"))
    @AttributeOverride(name = "street", column = @Column(name = "address_street"))
    private Address address;

    @Column(nullable = false)
    private LocalDate expiry;

    public CreditCard(Customer customer, String firstname, String lastname, String brand, String cardNumber, Address address, LocalDate expiry) {
        super(customer, firstname, lastname);
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.address = address;
        this.expiry = expiry;
    }

    public CreditCard() {
    }
}
