package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

@Service("workLog")
@Transactional()
public class WorkLogServiceImpl2 implements WorkLogService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepo.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepo.saveAndFlush(employee);
    }

}
