package iuh.fit.nguyenthimyduyen_22721461_mongodb.controller;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgAgeByStatusDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgSalaryByDeptIdDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.service.EmployeeAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics/employees")
@CrossOrigin(origins = "*")
public class EmployeeAnalyticsController {

    @Autowired
    private EmployeeAnalyticsService employeeAnalyticsService;

    /**
     * Lấy thống kê số lượng nhân viên và tuổi trung bình theo trạng thái
     *
     * @return ResponseEntity chứa danh sách AvgAgeByStatusDTO hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/stats/age-by-status")
    public ResponseEntity<List<AvgAgeByStatusDTO>> getEmployeeCountAndAvgAgeByStatus() {
        try {
            List<AvgAgeByStatusDTO> stats = employeeAnalyticsService.getEmployeeCountAndAvgAgeByStatus();
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy thống kê số lượng nhân viên và lương trung bình theo phòng ban
     *
     * @return ResponseEntity chứa danh sách AvgSalaryByDeptIdDTO hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/stats/salary-by-department")
    public ResponseEntity<List<AvgSalaryByDeptIdDTO>> getEmployeeCountAndAvgSalaryByDepartment() {
        try {
            List<AvgSalaryByDeptIdDTO> stats = employeeAnalyticsService.getEmployeeCountAndAvgSalaryByDepartment();
            return new ResponseEntity<>(stats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy danh sách nhân viên có lương cao nhất
     *
     * @return ResponseEntity chứa danh sách Employee hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/max-salary")
    public ResponseEntity<List<Employee>> getEmployeesWithMaxSalary() {
        try {
            List<Employee> employees = employeeAnalyticsService.getEmployeesWithMaxSalary();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy danh sách nhân viên có tuổi cao nhất
     *
     * @return ResponseEntity chứa danh sách Employee hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/max-age")
    public ResponseEntity<List<Employee>> getEmployeesWithMaxAge() {
        try {
            List<Employee> employees = employeeAnalyticsService.getEmployeesWithMaxAge();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy danh sách nhân viên được sắp xếp theo lương tăng dần trong mỗi phòng ban
     * @return ResponseEntity chứa bản đồ phòng ban và danh sách nhân viên hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/salary-ascending-by-department")
    public ResponseEntity<Map<String, List<Employee>>> getEmployeesBySalaryAscendingPerDepartment() {
        try {
            Map<String, List<Employee>> result = employeeAnalyticsService.getEmployeesBySalaryAscendingPerDepartment();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy tổng quan thống kê về nhân viên
     * @return ResponseEntity chứa bản đồ thống kê hoặc mã lỗi 500 nếu có lỗi xảy ra
     */
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getEmployeeStatisticsSummary() {
        try {
            Map<String, Object> summary = employeeAnalyticsService.getEmployeeStatisticsSummary();
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
