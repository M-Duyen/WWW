package iuh.fit.nguyenthimyduyen_tuan05_bai05.dao;


import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Department;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Employee;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private final DBUtil dbUtil;

    public DepartmentDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection connection = dbUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("id"));
                department.setName(rs.getString("name"));
                list.add(department);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int depId = rs.getInt("id");
                    String name = rs.getString("name");
                    return new Department(depId, name);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addDepartment (Department department){
        String sql = "INSERT INTO departments (name) VALUES (?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, department.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateDepartment (Department department){
        String sql = "UPDATE departments SET name = ? WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, department.getName());
            ps.setInt(2, department.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteDepartment(int id) {
        String sql = "DELETE FROM departments WHERE id=?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getEmployeeByDepartmentID(int id){
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department_id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()){
                    int empId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double salary = resultSet.getDouble("salary");
                    int departmentId = resultSet.getInt("department_id");

                    Department department = new Department();
                    department.setId(departmentId);
                    Employee employee = new Employee(empId, name, salary, department);
                    employees.add(employee);

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
    public List<Department> searchDepartments(String keyword) {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments WHERE name LIKE ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Department d = new Department();
                    d.setId(rs.getInt("id"));
                    d.setName(rs.getString("name"));
                    list.add(d);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }


}
