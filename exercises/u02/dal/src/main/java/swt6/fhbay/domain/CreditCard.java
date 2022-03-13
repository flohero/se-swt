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

    public CreditCard() {
    }

    public CreditCard(Customer customer, String firstname, String lastname, String brand, String cardNumber, Address address, LocalDate expiry) {
        super(customer, firstname, lastname);
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.address = address;
        this.expiry = expiry;
    }

    public CreditCard(String firstname, String lastname, String brand, String cardNumber, Address address, LocalDate expiry) {
        super(firstname, lastname);
        this.brand = brand;
        this.cardNumber = cardNumber;
        this.address = address;
        this.expiry = expiry;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "brand='" + brand + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", address=" + address +
                ", expiry=" + expiry +
                '}';
    }
}
