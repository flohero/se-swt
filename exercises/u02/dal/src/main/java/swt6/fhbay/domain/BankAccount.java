package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends PaymentMethod {
    @Column(nullable = false, unique = true)
    private String iban;

    public BankAccount() {
    }

    public BankAccount(Customer customer, String firstname, String lastname, String iban) {
        super(customer, firstname, lastname);
        this.iban = iban;
    }

    public BankAccount(String firstname, String lastname, String iban) {
        super(firstname, lastname);
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
