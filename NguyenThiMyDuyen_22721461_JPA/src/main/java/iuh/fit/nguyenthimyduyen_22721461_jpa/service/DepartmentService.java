package iuh.fit.nguyenthimyduyen_22721461_jpa.service;


import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Department;
import iuh.fit.nguyenthimyduyen_22721461_jpa.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Lấy tất cả phòng ban
     * @return List of departments
     */
    public List<Department> findAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }

    /**
     * Tìm phòng ban by ID
     * @param id
     * @return Optional chứa phòng ban nếu tìm thấy
     */
    public Optional<Department> findDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * Tìm phòng ban theo id kèm nhân viên
     * @param id
     * @return Optional chứa phòng ban nếu tìm thấy
     */
    public Optional<Department> findDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id);
    }

    /**
     * Tìm phòng ban theo tên (tương đối)
     * @param name
     * @return list phòng ban
     */
    public List<Department> findDepartmentsByName(String name) {
        return departmentRepository.findByDeptNameContaining(name);
    }

    /**
     * Thêm mới department
     * @param department
     * @return thêm mới department
     */
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Cập nhật department
     * @param department
     * @return câp nhật department
     */
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Xóa department by ID
     * @param id
     */
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
