package swt6.spring.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.model.Address;
import swt6.spring.model.Customer;
import swt6.spring.services.CustomerService;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Customer Commands")
public class CustomerCommands {

    private final CustomerService customerService;

    public CustomerCommands(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ShellMethod("Create a new customer")
    public Customer createCustomer(@ShellOption String firstname,
                                   @ShellOption String lastname,
                                   @ShellOption String email,
                                   @ShellOption String country,
                                   @ShellOption String zipCode,
                                   @ShellOption String city,
                                   @ShellOption String street) {
        return customerService.save(new Customer(firstname, lastname, email, new Address(country, zipCode, city, street)));
    }

    @ShellMethod("List all customers")
    public String listCustomers() {
        return customerService.findAll().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod("Find customer by Id")
    public String findCustomer(@ShellOption Long id) {
        return customerService.findById(id)
                .map(Customer::toString)
                .orElse(String.format("Customer with id %s not found", id));
    }
}
