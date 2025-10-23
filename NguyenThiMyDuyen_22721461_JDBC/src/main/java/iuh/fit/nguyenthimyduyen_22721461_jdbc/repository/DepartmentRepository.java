package iuh.fit.nguyenthimyduyen_22721461_jdbc.repository;

import iuh.fit.nguyenthimyduyen_22721461_jdbc.model.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {

    /**
     * Tìm phòng ban tương đối
     * @param deptName
     * @return
     */
    @Query("SELECT d FROM Department d WHERE d.deptName LIKE  %:name%")
    List<Department> findByDeptNameContaining(@Param("name") String deptName);

    /**
     * Tìm phòng ban theo id kèm nhân viên
     * @param deptId
     * @return
     */
    @Query("SELECT d, e FROM Department d LEFT JOIN FETCH d.employees e WHERE d.id = :deptId")
    Optional<Department> findByIdWithEmployees(@Param("deptId") Long deptId);
}
