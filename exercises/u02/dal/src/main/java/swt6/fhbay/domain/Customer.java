package swt6.fhbay.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Customer extends EntityBase {
    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private Address paymentAddress; // if payment address is null same as shipping address

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<PaymentMethod> paymentMethods;
}
