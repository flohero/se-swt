package swt6.spring.worklog.logic;

import java.util.List;
import java.util.Optional;
a
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;

public class WorkLogServiceImpl2 implements WorkLogService {
  
  private EmployeeRepository employeeRepo;
    
  public WorkLogServiceImpl2() {
  }

  public void setEmployeeRepo(EmployeeRepository employeeRepo) {
    this.employeeRepo = employeeRepo;
  }
  
  public Optional<Employee> findEmployeeById(Long id) {
    return employeeRepo.findById(id);
  }

  public List<Employee> findAllEmployees() {
    return employeeRepo.findAll();
  }

  public Employee syncEmployee(Employee employee) {
    return employeeRepo.saveAndFlush(employee);
  }

}
