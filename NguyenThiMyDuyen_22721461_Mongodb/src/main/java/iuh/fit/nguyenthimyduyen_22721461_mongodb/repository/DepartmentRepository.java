package iuh.fit.nguyenthimyduyen_22721461_mongodb.repository;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<Department, String> {
    Department findByDeptId(String deptId);
}
