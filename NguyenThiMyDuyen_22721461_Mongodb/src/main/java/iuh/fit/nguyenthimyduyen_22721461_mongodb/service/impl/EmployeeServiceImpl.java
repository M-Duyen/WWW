package iuh.fit.nguyenthimyduyen_22721461_mongodb.service.impl;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.repository.EmployeeRepository;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAllEmployees() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all employees: " + e.getMessage(), e);
        }
    }

    @Override
    public Employee findByEmpId(String empId) {
        try {
            if (empId == null || empId.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be null or empty");
            }
            return employeeRepository.findByEmpId(empId);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employee with ID: " + empId, e);
        }
    }

    @Override
    public List<Employee> findByEmpNameContaining(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Search name cannot be null or empty");
            }
            return employeeRepository.findByEmpNameContainingIgnoreCase(name);
        } catch (Exception e) {
            throw new RuntimeException("Error searching employees by name: " + name, e);
        }
    }

    @Override
    public List<Employee> findByStatus(int status) {
        try {
            return employeeRepository.findByStatus(status);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employees by status: " + status, e);
        }
    }

    @Override
    public List<Employee> findByDepTID(String depTID) {
        try {
            if (depTID == null || depTID.trim().isEmpty()) {
                throw new IllegalArgumentException("Department ID cannot be null or empty");
            }
            return employeeRepository.findByDeptId(depTID);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employees by department: " + depTID, e);
        }
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        try {

            if (employee.getId() == null && existsByEmpId(employee.getEmpId())) {
                throw new IllegalArgumentException("Employee with ID " + employee.getEmpId() + " already exists");
            }

            return employeeRepository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException("Error saving employee: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteEmployee(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee ID cannot be null or empty");
            }
            
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isEmpty()) {
                throw new IllegalArgumentException("Employee not found with ID: " + id);
            }
            
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee with ID: " + id, e);
        }
    }

    @Override
    public boolean existsByEmpId(String empId) {
        try {
            if (empId == null || empId.trim().isEmpty()) {
                return false;
            }
            return employeeRepository.existsByEmpId(empId);
        } catch (Exception e) {
            throw new RuntimeException("Error checking employee existence: " + empId, e);
        }
    }

    @Override
    public List<Employee> findActiveEmployees() {
        try {
            return employeeRepository.findByStatus(1); // Assuming 1 is active status
        } catch (Exception e) {
            throw new RuntimeException("Error finding active employees", e);
        }
    }

    @Override
    public long countEmployeesByDepartment(String depTID) {
        try {
            if (depTID == null || depTID.trim().isEmpty()) {
                throw new IllegalArgumentException("Department ID cannot be null or empty");
            }
            return employeeRepository.countByDeptId(depTID);
        } catch (Exception e) {
            throw new RuntimeException("Error counting employees in department: " + depTID, e);
        }
    }

    @Override
    public List<Employee> findEmployeesByDepartmentAndStatus(String deptId, int status) {
        try {
            if (deptId == null || deptId.trim().isEmpty()) {
                throw new IllegalArgumentException("Department ID cannot be null or empty");
            }
            return employeeRepository.findByDeptIdAndStatus(deptId, status);
        } catch (Exception e) {
            throw new RuntimeException("Error finding employees by department and status", e);
        }
    }



}
