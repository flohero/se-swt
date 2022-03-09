package swt6.fhbay.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Address extends EntityBase{

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;
}
