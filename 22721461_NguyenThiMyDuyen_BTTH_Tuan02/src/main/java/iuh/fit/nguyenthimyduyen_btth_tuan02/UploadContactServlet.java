package iuh.fit.nguyenthimyduyen_btth_tuan02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "uploadContactServlet", value = "/upload-contact")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UploadContactServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Part photoPart = request.getPart("photo");

        if (photoPart != null && photoPart.getSize() > 0) {
            try (InputStream photoInputStream = photoPart.getInputStream();
                 Connection conn = getConnection()) {

                String sql = "INSERT INTO contacts (first_name, last_name, photo) VALUES (?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setBlob(3, photoInputStream);
                    stmt.executeUpdate();
                }

                response.getWriter().println("Contact uploaded successfully!");
            } catch (Exception e) {
                throw new ServletException("Error saving contact: " + e.getMessage(), e);
            }
        } else {
            response.getWriter().println("No photo uploaded!");
        }
    }

    private Connection getConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        String jdbcURL = "jdbc:mariadb://localhost:3306/contact";
        String dbUser = "root";
        String dbPassword = "123456789";
        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }
}