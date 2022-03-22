package swt6.spring.basics.aop.logic;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("worklog")
public class WorkLogServiceImpl implements WorkLogService {

    private final Map<Long, Employee> employees = new HashMap<Long, Employee>();

    public WorkLogServiceImpl() {
        init();
    }

    private void init() {
        employees.put(1L, new Employee(1L, "Bill", "Gates"));
        employees.put(2L, new Employee(2L, "James", "Goslin"));
        employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
    }

    public Employee findEmployeeById(Long id) throws EmployeeIdNotFoundException {
        if (employees.get(id) == null)
            throw new EmployeeIdNotFoundException();
        return employees.get(id);
    }

    public List<Employee> findAllEmployees() {
        return new ArrayList<>(employees.values());
    }
}