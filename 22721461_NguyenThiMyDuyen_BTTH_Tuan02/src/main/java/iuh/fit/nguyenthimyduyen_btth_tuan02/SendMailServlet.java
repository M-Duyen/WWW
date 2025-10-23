package iuh.fit.nguyenthimyduyen_btth_tuan02;

import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import jakarta.persistence.Inheritance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

@WebServlet("/send-mail")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class SendMailServlet extends HttpServlet {
    public static final String USERNAME = "nguyenmyduyen2702@gmail.com";
    public static final String PASSWORD = "urku feyv hlzt isys";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String to = req.getParameter("to");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");

        Part filePart = req.getPart("file");
        try {
//            1 cau hinh SMTP
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true"); //TLS

//            2. Tao session co xac thuc
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
//                    return super.getPasswordAuthentication();
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

//            3. Tao message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject, "UTF-8");

//            4. Noi dung chinh
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body, "UTF-8");

//            5. File dinh kem
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            if (filePart != null && filePart.getSize() > 0) {
                MimeBodyPart attachPart = new MimeBodyPart();
                String fileName = filePart.getSubmittedFileName();
                InputStream fileContent = filePart.getInputStream();
                attachPart.setFileName(fileName);
                attachPart.setDataHandler(new DataHandler(new ByteArrayDataSource(fileContent,
                        getServletContext().getMimeType(fileName))));
                multipart.addBodyPart(attachPart); }
            // 6. Gán multipart vào message
            message.setContent(multipart);
            // 7. Gửi mail
            Transport.send(message);
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<h3>Gửi mail thành công!</h3>");

        }catch (Exception e){
            throw new ServletException("Loi gui mail: " + e.getMessage());
        }
    }
}
