package swt6.orm.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    //@JoinColumn(name = "EMPLOYEE_ID")
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    //@OneToOne(cascade = CascadeType.ALL)
    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name="ADDR_ZIPCODE"))
    @AttributeOverride(name = "city", column = @Column(name="ADDR_CITY"))
    @AttributeOverride(name = "street", column = @Column(name="ADDR_STREET"))
    private Address address;

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry == null) {
            throw new IllegalArgumentException("entry is null");
        }
        if (entry.getEmployee() != null) {
            entry.getEmployee()
                    .getLogbookEntries()
                    .remove(entry);
            entry.setEmployee(this);
        }
        logbookEntries.add(entry);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .add("address=" + address)
                .toString();
    }
}
