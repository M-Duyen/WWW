package iuh.fit.nguyenthimyduyen_22721461_mongodb.repository;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    
    Employee findByEmpId(String empId);
    
    List<Employee> findByEmpNameContainingIgnoreCase(String name);
    
    List<Employee> findByStatus(int status);
    
    List<Employee> findByDeptId(String deptId);
    
    List<Employee> findByDeptIdAndStatus(String deptId, int status);
    
    boolean existsByEmpId(String empId);
    
    long countByDeptId(String deptId);
    
    @Query("{'salary': {$gte: ?0, $lte: ?1}}")
    List<Employee> findBySalaryRange(double minSalary, double maxSalary);
    
    @Query("{'depTID': ?0, 'salary': {$gte: ?1}}")
    List<Employee> findByDepTIDAndSalaryGreaterThan(String deptId, double salary);
    
    @Query(value = "{'status': 1}", sort = "{'empName': 1}")
    List<Employee> findActiveEmployeesOrderByName();
    
    @Query("{'depTID': ?0}")
    List<Employee> findEmployeesByDepartment(String deptId);
}
