package iuh.fit.nguyenthimyduyen_22721461_jpa.repository;

import iuh.fit.nguyenthimyduyen_22721461_jpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    /**
     * Tìm nhân viên theo tên tương đối
     *
     * @param name
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name%")
    List<Employee> findByNameContaining(@Param("name") String name);

    /**
     * Tìm nhân viên theo độ tuổi
     *
     * @param minAge
     * @param maxAge
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE (YEAR(CURRENT_DATE) - YEAR(e.dob)) BETWEEN :minAge AND :maxAge")
    List<Employee> findByAge(@Param("minAge") int minAge, @Param("maxAge") int maxAge);

    /**
     * Tìm nhân viên theo mức lương
     *
     * @param minSalary
     * @param maxSalary
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findBySalaryBetween(@Param("minSalary") Double minSalary, @Param("maxSalary") Double maxSalary);

    /**
     * Tìm nhân viên theo phòng ban
     *
     * @param deptId
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.department.id = :deptId")
    List<Employee> findByDeptId(@Param("deptId") Long deptId);

    /**
     * Tìm nhân viên có ngày sinh trước một ngày nhất định
     *
     * @param date
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.dob < :date")
    List<Employee> findByDobBefore(@Param("date") java.time.LocalDate date);

    /**
     * Tìm nhân viên có ngày sinh sau một ngày nhất định
     *
     * @param date
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.dob > :date")
    List<Employee> findByDobAfter(@Param("date") java.time.LocalDate date);

    /**
     * Tìm nhân viên có ngày sinh trong khoảng
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.dob BETWEEN :startDate AND :endDate")
    List<Employee> findByDobBetween(@Param("startDate") java.time.LocalDate startDate, @Param("endDate") java.time.LocalDate endDate);

    /**
     * Tim nhân viên theo tên và mức lương
     * @param name Name to search for
     * @param minSalary
     * @param maxSalary
     * @return
     */
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:name% AND e.salary BETWEEN :minSalary AND :maxSalary")
    List<Employee> findByNameAndSalaryBetween(
        @Param("name") String name,
        @Param("minSalary") Double minSalary,
        @Param("maxSalary") Double maxSalary
    );

    /**
     * Find employees by department ID
     * @param departmentId ID of the department
     * @return List of employees in the department
     */
    @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") Long departmentId);

    /**
     * Find employees by role
     * @param role Role to search for
     * @return List of employees with the specified role
     */
    List<Employee> findByRole(String role);
}
