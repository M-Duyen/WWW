package iuh.fit.nguyenthimyduyen_tuan05_bai05.dao;

import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Department;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Employee;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private final DBUtil dbUtil;

    public EmployeeDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Employee> getAllEmployees (){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees e JOIN departments d ON e.department_id = d.id";
        try(Connection connection = dbUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ){
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double salary = resultSet.getDouble("salary");
                int departmentId = resultSet.getInt("department_id");

                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setSalary(salary);

                Department department = new Department();
                department.setId(departmentId);

                employee.setDepartment(department);
                employees.add(employee);
                            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    public Employee getEmployeeById(int id){
        String sql = "SELECT * FROM employees e JOIN departments d ON e.department_id = d.id WHERE e.id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try(ResultSet resultSet = ps.executeQuery()){
                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    int departmentId = resultSet.getInt("department_id");

                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setName(name);
                    employee.setSalary(salary);

                    Department department = new Department();
                    department.setId(departmentId);

                    employee.setDepartment(department);
                    return employee;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    public void addEmployee(Employee employee){
        String sql = "INSERT INTO employees (name, salary, department_id) VALUES (?, ?, ?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, employee.getName());
            ps.setDouble(2, employee.getSalary());
            ps.setInt(3, employee.getDepartment().getId());
            ps.executeUpdate();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void updateEmployee(Employee employee){
        String sql = "UPDATE employees SET name = ?, salary = ?, department_id = ? WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, employee.getName());
            ps.setDouble(2, employee.getSalary());
            ps.setInt(3, employee.getDepartment().getId());
            ps.setInt(4, employee.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteEmployee(int id){
        String sql = "DELETE FROM employees WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
