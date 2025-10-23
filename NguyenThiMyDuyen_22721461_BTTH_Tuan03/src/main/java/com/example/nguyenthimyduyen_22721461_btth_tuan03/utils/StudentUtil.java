package com.example.nguyenthimyduyen_22721461_btth_tuan03.utils;

import com.example.nguyenthimyduyen_22721461_btth_tuan03.model.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentUtil {
    private DataSource dataSource;

    public StudentUtil(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
    }
    public List<Student> getStudents() throws Exception{
        List<Student> students = new ArrayList<>();

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM student ORDER BY ID";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String dob = rs.getString("dob");
                String email = rs.getString("email");
                String mobile = rs.getString("mobile");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String city = rs.getString("city");
                String pincode = rs.getString("pincode");
                String state = rs.getString("state");
                String country = rs.getString("country");
                String hobbies = rs.getString("hobbies");
                String board10 = rs.getString("board10");
                String percentage10 = rs.getString("percentage10");
                String year10 = rs.getString("year10");
                String board12 = rs.getString("board12");
                String percentage12 = rs.getString("percentage12");
                String year12 = rs.getString("year12");
                String course = rs.getString("course");

                Student student = new Student();
                student.setId(id);
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setDob(dob);
                student.setEmail(email);
                student.setMobile(mobile);
                student.setGender(gender);
                student.setAddress(address);
                student.setCity(city);
                student.setPincode(pincode);
                student.setState(state);
                student.setCountry(country);

                if(hobbies != null && !hobbies.isEmpty()){
                    List<String> hobbiesList = List.of(hobbies.split(","));
                    student.setHobbies(hobbiesList);
                }
                student.setBoard10(board10);
                student.setPercentage10(percentage10);
                student.setYear10(year10);
                student.setBoard12(board12);
                student.setPercentage12(percentage12);
                student.setYear12(year12);
                student.setCourse(course);

                students.add(student);

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return students;
    }
    public void addStudent(Student student) throws Exception {
        String sql = "INSERT INTO student " +
                "(first_name, last_name, dob, email, mobile, gender, address, city, pincode, state, country, hobbies, board10, percentage10, year10, board12, percentage12, year12, course) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql);
        ) {
            // Set parameters
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getDob());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getMobile());
            pstmt.setString(6, student.getGender());
            pstmt.setString(7, student.getAddress());
            pstmt.setString(8, student.getCity());
            pstmt.setString(9, student.getPincode());
            pstmt.setString(10, student.getState());
            pstmt.setString(11, student.getCountry());

            // Xử lý Hobbies: chuyển List thành chuỗi để lưu vào CSDL
            String hobbiesStr = (student.getHobbies() != null) ? String.join(",", student.getHobbies()) : "";
            pstmt.setString(12, hobbiesStr);

            pstmt.setString(13, student.getBoard10());
            pstmt.setString(14, student.getPercentage10());
            pstmt.setString(15, student.getYear10());
            pstmt.setString(16, student.getBoard12());
            pstmt.setString(17, student.getPercentage12());
            pstmt.setString(18, student.getYear12());
            pstmt.setString(19, student.getCourse());

            // Thực thi câu lệnh
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error adding student to database", e);
        }
    }
}
