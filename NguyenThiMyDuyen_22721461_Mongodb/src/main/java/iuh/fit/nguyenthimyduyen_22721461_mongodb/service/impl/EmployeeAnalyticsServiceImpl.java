package iuh.fit.nguyenthimyduyen_22721461_mongodb.service.impl;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgAgeByStatusDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgSalaryByDeptIdDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.repository.EmployeeAnalyticsRepository;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.service.EmployeeAnalyticsService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeAnalyticsServiceImpl implements EmployeeAnalyticsService {

    @Autowired
    private EmployeeAnalyticsRepository employeeAnalyticsRepository;

    @Override
    public List<AvgAgeByStatusDTO> getEmployeeCountAndAvgAgeByStatus() {
        try {
            return employeeAnalyticsRepository.getCountandAvgAgeByStatus();
        } catch (Exception e) {
            throw new RuntimeException("Error getting employee statistics by status: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AvgSalaryByDeptIdDTO> getEmployeeCountAndAvgSalaryByDepartment() {
        try {
            return employeeAnalyticsRepository.getCountandAvgSalaryByDept();
        } catch (Exception e) {
            throw new RuntimeException("Error getting employee statistics by department: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> getEmployeesWithMaxSalary() {
        try {
            List<Document> documents = employeeAnalyticsRepository.findAllMaxSalaryEmployees();
            return convertDocumentsToEmployees(documents);
        } catch (Exception e) {
            throw new RuntimeException("Error getting employees with max salary: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Employee> getEmployeesWithMaxAge() {
        try {
            List<Document> documents = employeeAnalyticsRepository.findAllMaxAgeEmployees();
            return convertDocumentsToEmployees(documents);
        } catch (Exception e) {
            throw new RuntimeException("Error getting employees with max age: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, List<Employee>> getEmployeesBySalaryAscendingPerDepartment() {
        try {
            List<Document> documents = employeeAnalyticsRepository.getEmployeesBySalaryAscPerDept();
            Map<String, List<Employee>> result = new HashMap<>();

            for (Document doc : documents) {
                String deptId = doc.getString("deptId");
                List<Document> employeeDocs = (List<Document>) doc.get("employees");
                List<Employee> employees = convertDocumentsToEmployees(employeeDocs);
                result.put(deptId, employees);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Error getting employees by salary per department: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getEmployeeStatisticsSummary() {
        try {
            Map<String, Object> summary = new HashMap<>();

            List<AvgAgeByStatusDTO> ageStats = getEmployeeCountAndAvgAgeByStatus();
            List<AvgSalaryByDeptIdDTO> salaryStats = getEmployeeCountAndAvgSalaryByDepartment();

            summary.put("avgAgeByStatus", ageStats);
            summary.put("avgSalaryByDepartment", salaryStats);
            summary.put("totalActiveEmployees", ageStats.stream()
                    .filter(stat -> stat.getStatus() == 1)
                    .mapToLong(AvgAgeByStatusDTO::getCount)
                    .sum());
            summary.put("totalDepartments", salaryStats.size());
            summary.put("overallAvgSalary", salaryStats.stream()
                    .mapToDouble(stat -> stat.getAvgSalary() * stat.getCount())
                    .sum() / salaryStats.stream().mapToLong(AvgSalaryByDeptIdDTO::getCount).sum());

            return summary;
        } catch (Exception e) {
            throw new RuntimeException("Error generating employee statistics summary: " + e.getMessage(), e);
        }
    }

    private List<Employee> convertDocumentsToEmployees(List<Document> documents) {
        return documents.stream().map(this::convertDocumentToEmployee).collect(Collectors.toList());
    }

    private Employee convertDocumentToEmployee(Document doc) {
        Employee employee = new Employee();
        employee.setId(doc.getObjectId("_id").toString());
        employee.setEmpId(doc.getString("empId"));
        employee.setEmpName(doc.getString("empName"));
        employee.setAge(doc.getInteger("age", 0));
        employee.setSalary(doc.getDouble("salary"));
        employee.setStatus(doc.getInteger("status", 0));
        employee.setEmpId(doc.getString("deptId"));
        return employee;
    }
}
