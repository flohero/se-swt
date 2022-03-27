package swt6.spring.dtos;

import swt6.spring.model.Address;

public class CustomerDto extends BaseDto {

    private String firstname;

    private String lastname;

    private String emailAddress;

    private Address shippingAddress;

    private Address paymentAddress;

    public CustomerDto() {
    }

    public CustomerDto(Long id) {
        setId(id);
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

    @Override
    public String toString() {
        return getId() + ". " + firstname + " " + lastname;
    }
}
