package swt6.fhbay.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentMethod extends EntityBase {

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;
}
