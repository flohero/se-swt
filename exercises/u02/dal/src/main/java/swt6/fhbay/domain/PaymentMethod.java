package swt6.fhbay.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMethod extends EntityBase {

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public PaymentMethod() {
    }

    public PaymentMethod(Customer customer, String firstname, String lastname) {
        this.customer = customer;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
}
