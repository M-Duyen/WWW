package iuh.fit.nguyenthimyduyen_22721461_mongodb.controller;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Lấy tất cả nhân viên
     * @return ResponseEntity chứa danh sách tất cả nhân viên và mã trạng thái HTTP
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Lấy nhân viên theo empId
     * @param empId Mã nhân viên
     * @return ResponseEntity chứa nhân viên nếu tìm thấy, hoặc mã trạng thái NOT_FOUND nếu không tìm thấy
     */
    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String empId) {
        Employee employee = employeeService.findByEmpId(empId);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Tìm kiếm nhân viên theo tên (chứa chuỗi con, không phân biệt hoa thường)
     * @param name Chuỗi con trong tên nhân viên
     * @return ResponseEntity chứa danh sách nhân viên tìm được và mã trạng thái HTTP
     */
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployeesByName(@RequestParam String name) {
        List<Employee> employees = employeeService.findByEmpNameContaining(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Lấy danh sách nhân viên theo trạng thái
     * @param status Trạng thái nhân viên (0: inactive, 1: active)
     * @return ResponseEntity chứa danh sách nhân viên và mã trạng thái HTTP
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Employee>> getEmployeesByStatus(@PathVariable int status) {
        List<Employee> employees = employeeService.findByStatus(status);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Lấy danh sách nhân viên theo mã phòng ban
     * @param depTID Mã phòng ban
     * @return ResponseEntity chứa danh sách nhân viên và mã trạng thái HTTP
     */
    @GetMapping("/department/{depTID}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable String depTID) {
        List<Employee> employees = employeeService.findByDepTID(depTID);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * Tạo mới một nhân viên
     * @param employee Đối tượng Employee cần tạo
     * @return ResponseEntity chứa nhân viên vừa tạo và mã trạng thái CREATED
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     * Cập nhật thông tin một nhân viên theo empId
     * @param empId Mã nhân viên cần cập nhật
     * @param employee Đối tượng Employee chứa thông tin cập nhật
     * @return ResponseEntity chứa nhân viên đã cập nhật và mã trạng thái OK, hoặc NOT_FOUND nếu không tìm thấy
     */
    @PutMapping("/{empId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String empId, @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.findByEmpId(empId);
        if (existingEmployee != null) {
            employee.setId(existingEmployee.getId());
            Employee updatedEmployee = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Xóa một nhân viên theo empId
     * @param empId Mã nhân viên cần xóa
     * @return ResponseEntity với mã trạng thái NO_CONTENT nếu xóa thành công, hoặc NOT_FOUND nếu không tìm thấy
     */
    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String empId) {
        Employee existingEmployee = employeeService.findByEmpId(empId);
        if (existingEmployee != null) {
            employeeService.deleteEmployee(existingEmployee.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

