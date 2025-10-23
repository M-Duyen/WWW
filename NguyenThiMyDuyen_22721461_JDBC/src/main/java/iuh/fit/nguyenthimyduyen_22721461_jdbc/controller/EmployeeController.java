package iuh.fit.nguyenthimyduyen_22721461_jdbc.controller;

import iuh.fit.nguyenthimyduyen_22721461_jdbc.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_jdbc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id)
                .map(employee -> new ResponseEntity<>(employee, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        List<Employee> employees = employeeService.findEmployeesByName(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<Employee> employees = employeeService.findEmployeesByDepartment(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/role")
    public ResponseEntity<List<Employee>> getEmployeesByRole(@RequestParam String role) {
        List<Employee> employees = employeeService.findEmployeesByRole(role);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/salary")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary) {
        List<Employee> employees = employeeService.findEmployeesBySalaryRange(minSalary, maxSalary);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/search/name-salary")
    public ResponseEntity<List<Employee>> getEmployeesByNameAndSalaryRange(
            @RequestParam String name,
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary) {
        List<Employee> employees = employeeService.findEmployeesByNameAndSalaryRange(name, minSalary, maxSalary);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/age")
    public ResponseEntity<List<Employee>> getEmployeesByAgeRange(
            @RequestParam int minAge,
            @RequestParam int maxAge) {
        List<Employee> employees = employeeService.findEmployeesByAgeRange(minAge, maxAge);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    @GetMapping("/search/dob/before")
    public ResponseEntity<List<Employee>> getEmployeesBornBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Employee> employees = employeeService.findEmployeesBornBefore(date);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/dob/after")
    public ResponseEntity<List<Employee>> getEmployeesBornAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Employee> employees = employeeService.findEmployeesBornAfter(date);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/search/dob/between")
    public ResponseEntity<List<Employee>> getEmployeesBornBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Employee> employees = employeeService.findEmployeesBornBetween(startDate, endDate);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.findEmployeeById(id)
                .map(existingEmployee -> {
                    employee.setId(id);
                    Employee updatedEmployee = employeeService.updateEmployee(employee);
                    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
