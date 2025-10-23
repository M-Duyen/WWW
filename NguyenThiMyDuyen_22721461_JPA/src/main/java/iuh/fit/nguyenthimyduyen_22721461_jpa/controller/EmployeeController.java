package iuh.fit.nguyenthimyduyen_22721461_jpa.controller;

import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Department;
import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Employee;
import iuh.fit.nguyenthimyduyen_22721461_jpa.service.DepartmentService;
import iuh.fit.nguyenthimyduyen_22721461_jpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    /**
     * Hiển thị trang tìm kiếm nâng cao
     */
    @GetMapping("/advanced-search")
    public String showAdvancedSearchPage() {
        return "employee-advanced-search";
    }

    /**
     * Lấy tất cả nhân viên
     */
    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.findAllEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Xem chi tiết nhân viên
     */
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employee-detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Nhân viên không tồn tại!");
            return "redirect:/employees";
        }
    }

    /**
     * Tìm nhân viên theo tên
     */
    @GetMapping("/search")
    public String searchEmployeesByName(@RequestParam String search, Model model) {
        List<Employee> employees = employeeService.findEmployeesByName(search);
        model.addAttribute("employees", employees);
        model.addAttribute("searchQuery", search);
        return "employees";
    }

    /**
     * Lấy nhân viên theo phòng ban
     */
    @GetMapping("/department/{departmentId}")
    public String getEmployeesByDepartment(@PathVariable Long departmentId, Model model) {
        List<Employee> employees = employeeService.findEmployeesByDepartment(departmentId);
        Optional<Department> department = departmentService.findDepartmentById(departmentId);
        model.addAttribute("employees", employees);
        department.ifPresent(dept -> model.addAttribute("departmentName", dept.getDeptName()));
        return "employees";
    }

    /**
     * Lấy nhân viên theo chức vụ
     */
    @GetMapping("/search/role")
    public String getEmployeesByRole(@RequestParam String role, Model model) {
        List<Employee> employees = employeeService.findEmployeesByRole(role);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên theo mức lương trong khoảng
     */
    @GetMapping("/search/salary")
    public String getEmployeesBySalaryRange(
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesBySalaryRange(minSalary, maxSalary);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên theo tên và mức lương trong khoảng
     */
    @GetMapping("/search/name-salary")
    public String getEmployeesByNameAndSalaryRange(
            @RequestParam String name,
            @RequestParam Double minSalary,
            @RequestParam Double maxSalary,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesByNameAndSalaryRange(name, minSalary, maxSalary);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên theo độ tuổi trong khoảng
     */
    @GetMapping("/search/age")
    public String getEmployeesByAgeRange(
            @RequestParam int minAge,
            @RequestParam int maxAge,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesByAgeRange(minAge, maxAge);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên sinh trước một ngày nhất định
     */
    @GetMapping("/search/dob/before")
    public String getEmployeesBornBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesBornBefore(date);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên sinh sau một ngày nhất định
     */
    @GetMapping("/search/dob/after")
    public String getEmployeesBornAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesBornAfter(date);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Lấy nhân viên sinh trong khoảng
     */
    @GetMapping("/search/dob/between")
    public String getEmployeesBornBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        List<Employee> employees = employeeService.findEmployeesBornBetween(startDate, endDate);
        model.addAttribute("employees", employees);
        return "employees";
    }

    /**
     * Hiển thị form thêm nhân viên mới
     */
    @GetMapping("/new")
    public String showCreateEmployeeForm(Model model) {
        Employee employee = new Employee();
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("isEdit", false);
        return "employee-form";
    }

    /**
     * Xử lý thêm nhân viên mới
     */
    @PostMapping("/add")
    public String createEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        try {
            Department department = departmentService.findDepartmentById(employee.getDepartment().getId())
                    .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại!"));
            employee.setDepartment(department);

            employeeService.saveEmployee(employee);
            redirectAttributes.addFlashAttribute("message",
                    "✅ Nhân viên '" + employee.getName() + "' đã được thêm thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Lỗi: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    /**
     * Hiển thị form chỉnh sửa nhân viên
     */
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employeeOpt = employeeService.findEmployeeById(id);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            List<Department> departments = departmentService.findAllDepartments();

            model.addAttribute("employee", employee);
            model.addAttribute("departments", departments);
            model.addAttribute("isEdit", true);
            model.addAttribute("pageTitle", "Edit Employee - " + employee.getName());

            return "employee-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Nhân viên với ID " + id + " không tồn tại!");
            return "redirect:/employees";
        }
    }

    /**
     * Xử lý cập nhật nhân viên
     */
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable Long id,
            @ModelAttribute Employee employee,
            RedirectAttributes redirectAttributes) {
        try {
            Optional<Employee> existingEmployeeOpt = employeeService.findEmployeeById(id);

            if (existingEmployeeOpt.isPresent()) {
                employee.setId(id);

                Department department = departmentService.findDepartmentById(employee.getDepartment().getId())
                        .orElseThrow(() -> new RuntimeException("Phòng ban không tồn tại!"));
                employee.setDepartment(department);

                employeeService.updateEmployee(employee);
                redirectAttributes.addFlashAttribute("message",
                        "✅ Nhân viên '" + employee.getName() + "' đã được cập nhật thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "❌ Nhân viên không tồn tại!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Lỗi khi cập nhật: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    /**
     * Xóa nhân viên
     */
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Employee> employeeOpt = employeeService.findEmployeeById(id);
            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();
                String employeeName = employee.getName();
                employeeService.deleteEmployee(id);
                redirectAttributes.addFlashAttribute("message",
                        "✅ Nhân viên '" + employeeName + "' đã được xóa thành công!");
            } else {
                redirectAttributes.addFlashAttribute("error", "❌ Nhân viên không tồn tại!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ Lỗi khi xóa: " + e.getMessage());
        }
        return "redirect:/employees";
    }

}
