package iuh.fit.nguyenthimyduyen_tuan05_bai05.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private int id;
    private String name;
    private double salary;
    private Department department;
}
