package iuh.fit.nguyenthimyduyen_22721461_jpa.controller;


import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Department;
import iuh.fit.nguyenthimyduyen_22721461_jpa.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

//@RestController

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Lấy tất cả phòng ban
     *
     * @return List of all departments
     */
    @GetMapping
    public String getAllDepartments(Model model) {
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "departments";
    }

    /**
     * Lấy phòng ban by ID
     *
     * @param id
     * @return Department if found, 404 otherwise
     */
    @GetMapping("/{id}")
    public String getDepartmentById(@PathVariable Long id, Model model) {
        Department department = departmentService.findDepartmentById(id).orElse(null);
        model.addAttribute("department", department);
        return "department-form";
    }

    /**
     * Lấy phòng ban theo id kèm nhân viên
     *
     * @param id
     * @return Department with employees if found, 404 otherwise
     */
    @GetMapping("/{id}/with-employees")
    public ResponseEntity<Department> getDepartmentWithEmployees(@PathVariable Long id) {
        return departmentService.findDepartmentWithEmployees(id)
                .map(department -> new ResponseEntity<>(department, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Tim phòng ban theo tên (tương đối)
     *
     * @param search
     * @return List of matching departments
     */
    @GetMapping("/search")
    public String searchDepartmentsByName(@RequestParam String search, Model model) {
        List<Department> departments = departmentService.findDepartmentsByName(search);
        model.addAttribute("departments", departments);
        return "departments";
    }

    /**
     * Hiển thị form tạo mới phòng ban
     *
     * @param model
     * @return department-form view
     */
    @GetMapping("/new")
    public String showCreateDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("formAction", "/departments");
        return "department-form";
    }

    /**
     * Xử lý tạo mới hoặc cập nhật phòng ban
     *
     * @param department
     * @param redirectAttributes
     * @return department-form view
     */
    @PostMapping({"", "/{id}"}) // Xử lý cả tạo mới và update (dựa vào ID trong đối tượng)
    public String saveOrUpdateDepartment(@ModelAttribute Department department, RedirectAttributes redirectAttributes) {
        departmentService.saveDepartment(department);
        redirectAttributes.addFlashAttribute("message", "Phòng ban đã được lưu thành công!");
        return "redirect:/departments";
    }

    /**
     * Hiển thị form chỉnh sửa phòng ban
     *
     * @param id
     * @param model
     * @param redirectAttributes
     * @return department-form view if department exists, otherwise redirect to /departments with error message
     */
    @GetMapping("/edit/{id}")
    public String showEditDepartmentForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes){
        Optional<Department> department = departmentService.findDepartmentById(id);
        if(department.isPresent()){
            model.addAttribute("department", department.get());
            model.addAttribute("formAction", "/departments");
            return "department-form";
        }else {
            redirectAttributes.addFlashAttribute("error", "Phong ban không tồn tại!");
            return "redirect:/departments";
        }
    }


    /**
     * Xóa phòng ban by ID
     *
     * @param id
     * @return No content if successful, 404 if department not found
     */
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if(departmentService.findDepartmentById(id).isPresent()){
            departmentService.deleteDepartment(id);
            redirectAttributes.addFlashAttribute("message", "Phòng ban đã được xóa thành công!");

        }else {
            redirectAttributes.addFlashAttribute("error", "Phòng ban không tồn tại!");
        }
       return "redirect:/departments";
    }
}

