package swt6.spring.basics.aop.logic;

import java.util.List;

public interface WorkLogService {
    Employee findEmployeeById(Long id) throws EmployeeIdNotFoundException;

    List<Employee> findAllEmployees();
}
