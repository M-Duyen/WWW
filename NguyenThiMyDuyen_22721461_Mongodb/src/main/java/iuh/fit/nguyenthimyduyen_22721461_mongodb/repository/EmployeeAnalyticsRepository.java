package iuh.fit.nguyenthimyduyen_22721461_mongodb.repository;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgAgeByStatusDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgSalaryByDeptIdDTO;
import org.bson.Document;

import java.util.List;

public interface EmployeeAnalyticsRepository {

    List<AvgAgeByStatusDTO> getCountandAvgAgeByStatus();

    List<AvgSalaryByDeptIdDTO> getCountandAvgSalaryByDept();

    List<Document> findAllMaxSalaryEmployees();

    List<Document> findAllMaxAgeEmployees();

    List<Document> getEmployeesBySalaryAscPerDept();
}
