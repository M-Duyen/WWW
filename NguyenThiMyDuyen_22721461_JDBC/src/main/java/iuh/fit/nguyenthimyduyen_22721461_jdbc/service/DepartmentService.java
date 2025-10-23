package iuh.fit.nguyenthimyduyen_22721461_jdbc.service;

import iuh.fit.nguyenthimyduyen_22721461_jdbc.model.Department;
import iuh.fit.nguyenthimyduyen_22721461_jdbc.repository.DepartmentRepository;
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
     * Tìm phòng ban theo ID
     * @param id
     * @return Optional chứa phòng ban nếu tìm thấy
     */
    public Optional<Department> findDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * Tìm phòng ban theo ID kèm tất cả nhân viên
     * @param id
     * @return Optional chứa phòng ban với nhân viên nếu tìm thấy
     */
    public Optional<Department> findDepartmentWithEmployees(Long id) {
        return departmentRepository.findByIdWithEmployees(id);
    }

    /**
     * Tìm phòng ban theo tên tương đối
     * @param name
     * @return List of phòng ban với tên tương ứng
     */
    public List<Department> findDepartmentsByName(String name) {
        return departmentRepository.findByDeptNameContaining(name);
    }

    /**
     * Thêm mới phòng ban
     * @param department
     * @return Saved department
     */
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Cập nhật phòng ban
     * @param department
     * @return Updated department
     */
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * Xoa phòng ban theo ID
     * @param id Department
     */
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
