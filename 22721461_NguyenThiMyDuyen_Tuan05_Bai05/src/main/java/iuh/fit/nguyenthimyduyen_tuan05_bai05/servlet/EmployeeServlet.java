package iuh.fit.nguyenthimyduyen_tuan05_bai05.servlet;

import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Department;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Employee;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.dao.DepartmentDAO;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.dao.EmployeeDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeServlet", value = "/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    private DepartmentDAO departmentDAO;

    @Resource(name = "jdbc/employeedb")
    private DataSource dataSource;

    public EmployeeServlet() {
    }

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new RuntimeException("Datasource is null");
        }
        employeeDAO = new EmployeeDAO(dataSource);
        departmentDAO = new DepartmentDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("form".equals(action)) {
            // Lấy danh sách departments
            List<Department> departments = departmentDAO.getAllDepartments();
            req.setAttribute("departments", departments);

            String idStr = req.getParameter("id");
            if (idStr != null) {
                int id = Integer.parseInt(idStr);
                Employee employee = employeeDAO.getEmployeeById(id);
                req.setAttribute("employee", employee);
            }

            req.getRequestDispatcher("/employee-form.jsp").forward(req, resp);

        } else {
            List<Employee> employees = employeeDAO.getAllEmployees();
            req.setAttribute("employees", employees);
            req.getRequestDispatcher("/employee-list.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("add".equals(action)){
            String name = req.getParameter("name");
            double salary = Double.parseDouble(req.getParameter("salary"));
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));

            Department department = departmentDAO.getDepartmentById(departmentId);
            if(department != null){
                Employee employee = new Employee();
                employee.setName(name);
                employee.setSalary(salary);
                employee.setDepartment(department);
                employeeDAO.addEmployee(employee);
            }
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            double salary = Double.parseDouble(req.getParameter("salary"));
            int departmentId = Integer.parseInt(req.getParameter("departmentId"));

            Department department = departmentDAO.getDepartmentById(departmentId);
            if(department != null){
                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setSalary(salary);
                employee.setDepartment(department);
                employeeDAO.updateEmployee(employee);
            }
        } else if ("delete".equals(action)){
            int id = Integer.parseInt(req.getParameter("id"));
            employeeDAO.deleteEmployee(id);

        }
        resp.sendRedirect(req.getContextPath() + "/employees");
    }
}
