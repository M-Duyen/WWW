package iuh.fit.nguyenthimyduyen_22721461_jdbc.dao.impl;

import iuh.fit.nguyenthimyduyen_22721461_jdbc.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDAOImpl implements iuh.fit.nguyenthimyduyen_22721461_jdbc.dao.EmployeeDAO {
    private JdbcTemplate jdbcTemplate;

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Employee> rowMapper = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employee(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("role")
            );
        }
    };
    @Override
    public void save(Employee employee){
        String sql = "INSERT INTO employees (name, role) VALUES (?, ?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getRole());
    }

    @Override
    public void update(Employee employee){
        String sql = "UPDATE employees SET name = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(sql, employee.getName(), employee.getRole(), employee.getId());
    }

    @Override
    public Employee getById(long id){
        String sql = "SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
    @Override
    public List<Employee> getAll(){
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public void deleteById(long id){
        String sql = "DELETE FROM employees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
