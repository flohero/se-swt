package swt6.orm.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PE")
public class PermanentEmployee extends Employee {
  @Id
  @GeneratedValue
  private Long id;
  private double salary;

  public PermanentEmployee() {  
  }
  
  public PermanentEmployee(String firstName, String lastName, LocalDate dateOfBirth) {
    super(firstName, lastName, dateOfBirth);
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }
  
  public String toString() {
    return super.toString() + ", salary=" + salary;
  }
}
