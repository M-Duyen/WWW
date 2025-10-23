package iuh.fit.nguyenthimyduyen_22721461_jpa.service;

import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_jpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Lấy tất cả nhân viên
     * @return List of all employees
     */
    public List<Employee> findAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    /**
     * Tìm nhân viên by ID
     * @param id
     * @return Optional chưa nhân viên nếu tìm thấy
     */
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Tim nhân viên theo tên (tương đối)
     * @param name
     * @return List of employees
     */
    public List<Employee> findEmployeesByName(String name) {
        return employeeRepository.findByNameContaining(name);
    }

    /**
     * Tìm nhân viên theo phòng ban
     * @param departmentId
     * @return List of employees thuộc phòng ban
     */
    public List<Employee> findEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    /**
     * Tìm nhân viên theo chức vụ
     * @param role
     * @return List of employees theo chức vụ
     */
    public List<Employee> findEmployeesByRole(String role) {
        return employeeRepository.findByRole(role);
    }

    /**
     * Tìm nhân viên theo mức lương trong khoảng
     * @param minSalary
     * @param maxSalary
     * @return List of employees với mức lương trong khoảng
     */
    public List<Employee> findEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }

    /**
     * Tìm nhân viên theo độ tuổi trong khoảng
     * @param minAge
     * @param maxAge
     * @return List of employees theo độ tuổi trong khoảng
     */
    public List<Employee> findEmployeesByAgeRange(int minAge, int maxAge) {
        return employeeRepository.findByAge(minAge, maxAge);
    }

    /**
     * Tìm nhân viên có ngày sinh trước một ngày nhất định
     * @param date
     * @return List of employees sinh trước ngày
     */
    public List<Employee> findEmployeesBornBefore(LocalDate date) {
        return employeeRepository.findByDobBefore(date);
    }

    /**
     * Tìm nhân viên có ngày sinh sau một ngày nhất định
     * @param date
     * @return List of employees sinh sau ngày
     */
    public List<Employee> findEmployeesBornAfter(LocalDate date) {
        return employeeRepository.findByDobAfter(date);
    }

    /**
     * Tìm nhân viên có ngày sinh trong khoảng
     * @param startDate
     * @param endDate
     * @return List of employees sinh trong khoảng
     */
    public List<Employee> findEmployeesBornBetween(LocalDate startDate, LocalDate endDate) {
        return employeeRepository.findByDobBetween(startDate, endDate);
    }

    /**
     * Tìm nhân viên theo tên và mức lương trong khoảng
     * @param name
     * @param minSalary
     * @param maxSalary
     * @return List of matching employees
     */
    public List<Employee> findEmployeesByNameAndSalaryRange(String name, Double minSalary, Double maxSalary) {
        return employeeRepository.findByNameAndSalaryBetween(name, minSalary, maxSalary);
    }

    /**
     * Thêm mới nhân viên
     * @param employee
     * @return Saved employee
     */
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Câp nhật nhân viên
     * @param employee
     * @return Updated employee
     */
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Xoa nhân viên by ID
     * @param id
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
