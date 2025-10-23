package iuh.fit.ongk_de04.servlet;

import iuh.fit.ongk_de04.dao.LoaiThuocDAO;
import iuh.fit.ongk_de04.dao.ThuocDAO;
import iuh.fit.ongk_de04.model.LoaiThuoc;
import iuh.fit.ongk_de04.model.Thuoc;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ThuocServlet", value = "/thuoc")
public class ThuocServlet extends HttpServlet {
    private ThuocDAO thuocDAO;
    private LoaiThuocDAO loaiThuocDAO;

    @Resource(name = "jdbc/quanlythuoc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("DataSource is null");
        }
        thuocDAO = new ThuocDAO(dataSource);
        loaiThuocDAO = new LoaiThuocDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equals(action)) {
            List<LoaiThuoc> loaiThuocs = loaiThuocDAO.getAllLoaiThuoc();
            req.setAttribute("loaiThuocs", loaiThuocs);
            req.getRequestDispatcher("/thuoc-form.jsp").forward(req, resp);
        }else{
            List<Thuoc> thuocs = thuocDAO.getAllThuoc();
            req.setAttribute("thuocs", thuocs);
            req.getRequestDispatcher("/thuoc-list.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String tenThuoc = req.getParameter("tenThuoc");
            double gia = Double.parseDouble(req.getParameter("gia"));
            int namSX = Integer.parseInt(req.getParameter("namSX"));

            int maLoai = Integer.parseInt(req.getParameter("maLoai"));

            LoaiThuoc loaiThuoc = new LoaiThuoc();
            loaiThuoc.setMaLoai(maLoai);

            Thuoc thuoc = new Thuoc();
            thuoc.setTenThuoc(tenThuoc);
            thuoc.setGia(gia);
            thuoc.setNamSX(namSX);
            thuoc.setLoaiThuoc(loaiThuoc);

            thuocDAO.addThuoc(thuoc);
        }
        resp.sendRedirect(req.getContextPath() + "/thuoc");
    }
}
