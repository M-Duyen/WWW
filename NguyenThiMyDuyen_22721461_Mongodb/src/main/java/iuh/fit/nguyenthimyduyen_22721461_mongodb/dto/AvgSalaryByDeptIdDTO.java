package iuh.fit.nguyenthimyduyen_22721461_mongodb.dto;

public class AvgSalaryByDeptIdDTO {
    private String deptId;
    private long count;
    private double avgSalary;

    public AvgSalaryByDeptIdDTO() {
    }

    public AvgSalaryByDeptIdDTO(String deptId, long count, double avgSalary) {
        this.deptId = deptId;
        this.count = count;
        this.avgSalary = avgSalary;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(double avgSalary) {
        this.avgSalary = avgSalary;
    }

    @Override
    public String toString() {
        return "AvgSalaryByDeptIdDTO{" +
                "deptId='" + deptId + '\'' +
                ", count=" + count +
                ", avgSalary=" + avgSalary +
                '}';
    }
}
