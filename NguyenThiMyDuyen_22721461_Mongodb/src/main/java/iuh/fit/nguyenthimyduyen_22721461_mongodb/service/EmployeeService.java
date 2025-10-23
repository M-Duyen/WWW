package iuh.fit.nguyenthimyduyen_22721461_mongodb.service;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;

import java.util.List;

public interface EmployeeService {
    
    List<Employee> findAllEmployees();
    
    Employee findByEmpId(String empId);
    
    List<Employee> findByEmpNameContaining(String name);
    
    List<Employee> findByStatus(int status);
    
    List<Employee> findByDepTID(String depTID);
    
    Employee saveEmployee(Employee employee);
    
    void deleteEmployee(String id);
    
    boolean existsByEmpId(String empId);
    
    List<Employee> findActiveEmployees();
    
    long countEmployeesByDepartment(String depTID);
    
    List<Employee> findEmployeesByDepartmentAndStatus(String depTID, int status);
}
