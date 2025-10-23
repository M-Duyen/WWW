package iuh.fit.nguyenthimyduyen_22721461_mongodb.dto;

public class AvgAgeByStatusDTO {
    private int status;
    private long count;
    private double avgAge;

    public AvgAgeByStatusDTO() {
    }

    public AvgAgeByStatusDTO(int status, long count, double avgAge) {
        this.status = status;
        this.count = count;
        this.avgAge = avgAge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(double avgAge) {
        this.avgAge = avgAge;
    }

    @Override
    public String toString() {
        return "AvgAgeByStatusDTO{" +
                "status=" + status +
                ", count=" + count +
                ", avgAge=" + avgAge +
                '}';
    }
}
