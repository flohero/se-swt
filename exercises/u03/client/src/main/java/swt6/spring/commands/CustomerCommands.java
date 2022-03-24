package swt6.spring.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellOption;
import swt6.spring.model.Customer;
import swt6.spring.services.CustomerService;

@ShellComponent
public class CustomerCommands {

    private final CustomerService customerService;

    public CustomerCommands(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createCustomer(@ShellOption() String firstname,
                               @ShellOption() String lastname) {
        Customer customer = customerService.save(null);
    }
}
