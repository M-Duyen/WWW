package iuh.fit.nguyenthimyduyen_22721461_mongodb.repository;

import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgAgeByStatusDTO;
import iuh.fit.nguyenthimyduyen_22721461_mongodb.dto.AvgSalaryByDeptIdDTO;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class EmployeeAnalyticsRepositoryImpl implements EmployeeAnalyticsRepository {
        private MongoTemplate mongoTemplate;

        public EmployeeAnalyticsRepositoryImpl(MongoTemplate mongoTemplate) {
                this.mongoTemplate = mongoTemplate;
        }

        // đếm số nhân viên và tính lương trung bình theo từng status
        // db.employees.aggregate([
        // {
        // $group: {
        // _id: "$status",
        // employeeCount: { $sum: 1 },
        // averageAge: { $avg: "$age" }
        // }
        // }
        // ])
        @Override
        public List<AvgAgeByStatusDTO> getCountandAvgAgeByStatus() {
                Aggregation agg = newAggregation(
                                group("status")
                                                .count().as("count")
                                                .avg("age").as("avgAge"),
                                project("count", "avgAge")
                                                .and("_id").as("status"));
                AggregationResults<AvgAgeByStatusDTO> results = mongoTemplate.aggregate(agg, "employees",
                                AvgAgeByStatusDTO.class);
                return results.getMappedResults();
        }

        // Đếm số nhân viên theo từng mã phòng ban và tính độ tuổi trung bình trong mỗi
        // phòng ban
        // db.employees.aggregate([
        // {
        // $group: {
        // _id: "$deptId",
        // count: { $sum: 1 },
        // avgSalary: { $avg: "$salary" }
        // }
        // }
        // ])
        @Override
        public List<AvgSalaryByDeptIdDTO> getCountandAvgSalaryByDept() {
                Aggregation agg = newAggregation(
                                group("depTID")
                                                .count().as("count")
                                                .avg("salary").as("avgSalary"),
                                project("count", "avgSalary")
                                                .and("_id").as("deptId"));
                AggregationResults<AvgSalaryByDeptIdDTO> results = mongoTemplate.aggregate(agg, "employees",
                                AvgSalaryByDeptIdDTO.class);
                return results.getMappedResults();
        }

        // Xuất ra danh sách employee có lương cao nhất
        // db.employees.aggregate([
        // {
        // $group: {
        // _id: null,
        // maxSalary: { $max: "$salary" }
        // }
        // },
        // {
        // $lookup: {
        // from: "employees",
        // localField: "maxSalary",
        // foreignField: "salary",
        // as: "employees"
        // }
        // },
        // {
        // $unwind: "$employees"
        // },
        // {
        // $replaceRoot: { newRoot: "$employees" }
        // }
        // ])
        @Override
        public List<Document> findAllMaxSalaryEmployees() {
                Aggregation agg = newAggregation(
                                group()
                                                .max("salary").as("maxSalary"),
                                lookup("employees", "maxSalary", "salary", "employees"),
                                unwind("employees"),
                                replaceRoot("employees"));
                AggregationResults<Document> maxSalaryResults = mongoTemplate.aggregate(agg, "employees",
                                Document.class);
                return maxSalaryResults.getMappedResults();
        }

        // Xuất ra danh sách employee có tuổi cao nhất
        @Override
        public List<Document> findAllMaxAgeEmployees() {
                Aggregation agg = newAggregation(
                                group()
                                                .max("age").as("maxAge"),
                                lookup("employees", "maxAge", "age", "employees"),
                                unwind("employees"),
                                replaceRoot("employees"));
                AggregationResults<Document> maxAgeResults = mongoTemplate.aggregate(agg, "employees", Document.class);
                return maxAgeResults.getMappedResults();
        }

        // Xuất ra danh sách employee theo mức lương tăng dần từng theo department
        @Override
        public List<Document> getEmployeesBySalaryAscPerDept() {
                Aggregation agg = newAggregation(
                                sort(Sort.by(Sort.Direction.ASC, "depTID", "salary")),
                                group("depTID")
                                                .push("$$ROOT").as("employees"),
                                project()
                                                .and("_id").as("deptId")
                                                .and("employees").as("employees"));
                AggregationResults<Document> results = mongoTemplate.aggregate(agg, "employees", Document.class);
                return results.getMappedResults();
        }
}
