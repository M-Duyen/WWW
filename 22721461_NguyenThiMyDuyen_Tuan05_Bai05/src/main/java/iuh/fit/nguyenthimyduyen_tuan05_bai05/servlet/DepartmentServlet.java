package iuh.fit.nguyenthimyduyen_tuan05_bai05.servlet;

import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Department;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.beans.Employee;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.dao.DepartmentDAO;
import iuh.fit.nguyenthimyduyen_tuan05_bai05.dao.EmployeeDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet(name = "DepartmentServlet", value = "/departments")
public class DepartmentServlet extends HttpServlet {
    private DepartmentDAO departmentDAO;
    private EmployeeDAO employeeDAO;

    @Resource(name = "jdbc/employeedb")
    private DataSource dataSource;

    public DepartmentServlet() {
    }

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new RuntimeException("Datasource is null");
        }
        departmentDAO = new DepartmentDAO(dataSource);
        employeeDAO = new EmployeeDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("edit".equals(action)){
            int id = Integer.parseInt(req.getParameter("id"));
            Department department = departmentDAO.getDepartmentById(id);
            req.setAttribute("department", department);
            req.getRequestDispatcher("/department-form.jsp").forward(req, resp);
        } else if ("search".equals(action)) {
            String keyword = req.getParameter("keyword");
            List<Department> departments = departmentDAO.searchDepartments(keyword);
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("/department-list.jsp").forward(req, resp);
        } else{
            List<Department> departments =departmentDAO.getAllDepartments();
            req.setAttribute("departments", departments);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/department-list.jsp");
            requestDispatcher.forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try{
            if("add".equals(action)){
                String name = req.getParameter("name");
                Department department = new Department();
                department.setName(name);
                departmentDAO.addDepartment(department);
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                Department department = new Department();
                department.setId(id);
                department.setName(name);
                departmentDAO.updateDepartment(department);
            }else if ("delete".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                departmentDAO.deleteDepartment(id);
            } else if ("list".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                List<Employee> employees = departmentDAO.getEmployeeByDepartmentID(id);
                req.setAttribute("employees", employees);
                req.getRequestDispatcher("/employee-list.jsp").forward(req, resp);
            }

            resp.sendRedirect("departments");
        } catch (RuntimeException e) {
            String error = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            resp.sendRedirect("departments?error=" + error);
        }
    }
}
