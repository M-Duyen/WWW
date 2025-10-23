package com.example.nguyenthimyduyen_22721461_btth_tuan03.servlet;

import com.example.nguyenthimyduyen_22721461_btth_tuan03.model.Student;
import com.example.nguyenthimyduyen_22721461_btth_tuan03.utils.StudentUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "studentServlet", value = "/student-servlet")
public class StudentServlet extends HttpServlet {
    @Resource(name = "jdbc/storedb")
    private DataSource dataSource;
    private StudentUtil studentUtil;

    public StudentServlet() {
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Student student = new Student();
//
//        student.setFirstName(req.getParameter("id"));
//        student.setFirstName(req.getParameter("firstName"));
//        student.setLastName(req.getParameter("lastName"));
//        student.setDob(req.getParameter("dob"));
//        student.setEmail(req.getParameter("email"));
//        student.setMobile(req.getParameter("mobile"));
//        student.setGender(req.getParameter("gender"));
//        student.setAddress(req.getParameter("address"));
//        student.setCity(req.getParameter("city"));
//        student.setPincode(req.getParameter("pincode"));
//        student.setState(req.getParameter("state"));
//        student.setCountry(req.getParameter("country"));
//        String[] hobbiesArray = req.getParameterValues("hobbies");
//        if (hobbiesArray != null) {
//            List<String> hobbiesList = Arrays.asList(hobbiesArray);
//            student.setHobbies(hobbiesList);
//        }
//
//        student.setBoard10(req.getParameter("board10"));
//        student.setPercentage10(req.getParameter("percentage10"));
//        student.setYear10(req.getParameter("year10"));
//        student.setBoard12(req.getParameter("board12"));
//        student.setPercentage12(req.getParameter("percentage12"));
//        student.setYear12(req.getParameter("year12"));
//
//
//        student.setCourse(req.getParameter("course"));
//
//        System.out.println("Student registered: " + student.toString());
//
//        req.setAttribute("student", student);
//        req.getRequestDispatcher("student-result.jsp").forward(req, resp);
//    }


    @Override
    public void init() throws ServletException {
        try {
            DataSource ds = (DataSource) getServletContext().getAttribute("dataSource");
            studentUtil = new StudentUtil(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        try {
            student.setFirstName(req.getParameter("firstName"));
            student.setLastName(req.getParameter("lastName"));
            student.setDob(req.getParameter("dob"));
            student.setEmail(req.getParameter("email"));
            student.setMobile(req.getParameter("mobile"));
            student.setGender(req.getParameter("gender"));
            student.setAddress(req.getParameter("address"));
            student.setCity(req.getParameter("city"));
            student.setPincode(req.getParameter("pincode"));
            student.setState(req.getParameter("state"));
            student.setCountry(req.getParameter("country"));
            String[] hobbiesArray = req.getParameterValues("hobbies");
            if (hobbiesArray != null) {
                List<String> hobbiesList = Arrays.asList(hobbiesArray);
                student.setHobbies(hobbiesList);
            }

            student.setBoard10(req.getParameter("board10"));
            student.setPercentage10(req.getParameter("percentage10"));
            student.setYear10(req.getParameter("year10"));
            student.setBoard12(req.getParameter("board12"));
            student.setPercentage12(req.getParameter("percentage12"));
            student.setYear12(req.getParameter("year12"));


            student.setCourse(req.getParameter("course"));

            studentUtil.addStudent(student);

            req.setAttribute("student", student);
            req.getRequestDispatcher("student-result.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save student data.");
        }
    }
}
