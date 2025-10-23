package iuh.fit.nguyenthimyduyen_btth_tuan02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "uploadServlet", value = "/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Correct path to src/main/uploads
//        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
//        String uploadPath = System.getProperty("user.dir")
//                + File.separator + "src"
//                + File.separator + "main"
//                + File.separator + "uploads";
//        String uploadPath = "D:\\File_hoc_tap\\Nam_4\\Ky_1\\WWW\\22721461_NguyenThiMyDuyen\\NguyenThiMyDuyen_BTTH_Tuan02\\src\\main\\uploads";
        String projectPath = new File(getServletContext().getRealPath("/"))
                .getParentFile()
                .getParentFile()
                .getAbsolutePath();
        String uploadPath = projectPath
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "uploads";
        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Process uploaded files
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            if (fileName != null && !fileName.isEmpty()) {
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath); // Save the file
            }
        }

        resp.getWriter().println("File uploaded successfully to " + uploadPath);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }

        return null;
    }
}
