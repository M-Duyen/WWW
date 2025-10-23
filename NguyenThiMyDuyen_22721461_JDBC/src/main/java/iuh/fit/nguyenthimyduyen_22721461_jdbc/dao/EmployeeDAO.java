package iuh.fit.nguyenthimyduyen_22721461_jdbc.dao;

import iuh.fit.nguyenthimyduyen_22721461_jdbc.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    void save(Employee employee);

    void update(Employee employee);

    Employee getById(long id);

    List<Employee> getAll();

    void deleteById(long id);
}
