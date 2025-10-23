package iuh.fit.nguyenthimyduyen_22721461_mongodb.service;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgAgeByStatusDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgSalaryByDeptIdDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeAnalyticsService {

    List<AvgAgeByStatusDTO> getEmployeeCountAndAvgAgeByStatus();

    List<AvgSalaryByDeptIdDTO> getEmployeeCountAndAvgSalaryByDepartment();

    List<Employee> getEmployeesWithMaxSalary();

    List<Employee> getEmployeesWithMaxAge();

    Map<String, List<Employee>> getEmployeesBySalaryAscendingPerDepartment();

    Map<String, Object> getEmployeeStatisticsSummary();
}
