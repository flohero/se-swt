package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends PaymentMethod {
    @Column(nullable = false)
    private String iban;
}
