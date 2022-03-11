package swt6.fhbay.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends EntityBase {
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Embedded
    @AttributeOverride(name = "country", column = @Column(name = "shippingAddress_country"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "shippingAddress_zipCode"))
    @AttributeOverride(name = "city", column = @Column(name = "shippingAddress_city"))
    @AttributeOverride(name = "street", column = @Column(name = "shippingAddress_street"))
    private Address shippingAddress;

    @Embedded
    @AttributeOverride(name = "country", column = @Column(name = "paymentAddress_country"))
    @AttributeOverride(name = "zipCode", column = @Column(name = "paymentAddress_zipCode"))
    @AttributeOverride(name = "city", column = @Column(name = "paymentAddress_city"))
    @AttributeOverride(name = "street", column = @Column(name = "paymentAddress_street"))
    private Address paymentAddress;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    public Customer() {

    }

    public Customer(String firstname, String lastname, String emailAddress, Address shippingAddress, Address paymentAddress, Set<PaymentMethod> paymentMethods) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.shippingAddress = shippingAddress;
        this.paymentAddress = paymentAddress;
        this.paymentMethods = paymentMethods;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(Address paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(Set<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("PaymentMethod cannot be null");
        }
        if (paymentMethod.getCustomer() != null) {
            paymentMethod.getCustomer()
                    .getPaymentMethods()
                    .remove(paymentMethod);
        }
        paymentMethod.setCustomer(this);
        paymentMethods.add(paymentMethod);
    }
}
