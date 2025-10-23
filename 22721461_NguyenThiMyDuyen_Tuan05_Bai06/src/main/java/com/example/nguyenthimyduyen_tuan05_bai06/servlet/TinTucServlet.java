package com.example.nguyenthimyduyen_tuan05_bai06.servlet;


import com.example.nguyenthimyduyen_tuan05_bai06.dao.DanhMucDAO;
import com.example.nguyenthimyduyen_tuan05_bai06.dao.TinTucDAO;
import com.example.nguyenthimyduyen_tuan05_bai06.model.DanhMuc;
import com.example.nguyenthimyduyen_tuan05_bai06.model.TinTuc;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@WebServlet(name = "TinTucServlet", value = "/tintuc")
public class TinTucServlet extends HttpServlet {
    private TinTucDAO tinTucDAO;
    private DanhMucDAO danhMucDAO;
    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("Datasource is null");
        }
        tinTucDAO = new TinTucDAO(dataSource);
        danhMucDAO = new DanhMucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            List<DanhMuc> danhMucs = danhMucDAO.getAllDanhMuc();
            req.setAttribute("danhMucs", danhMucs);

            int id = Integer.parseInt(req.getParameter("maTT"));
            TinTuc tinTuc = tinTucDAO.getByID(id);
            req.setAttribute("tinTuc", tinTuc);
            req.getRequestDispatcher("/tinTuc-form.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            List<DanhMuc> danhMucs = danhMucDAO.getAllDanhMuc();
            req.setAttribute("danhMucs", danhMucs);

            req.getRequestDispatcher("/tinTuc-form.jsp").forward(req, resp);

        } else {
            List<TinTuc> tinTucs = tinTucDAO.getAllTinTuc();
            req.setAttribute("tinTucs", tinTucs);
            req.getRequestDispatcher("/tinTuc-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String tieuDe = req.getParameter("tieuDe");
            String noiDung = req.getParameter("noiDung");
            String lienKet = req.getParameter("lienKet");
            int danhMucId = Integer.parseInt(req.getParameter("maDM"));

            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setMaDM(danhMucId);

            TinTuc tinTuc = new TinTuc();
            tinTuc.setTieuDe(tieuDe);
            tinTuc.setNoiDung(noiDung);
            tinTuc.setLienKet(lienKet);
            tinTuc.setDanhMuc(danhMuc);

            tinTucDAO.addTinTuc(tinTuc);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("maTT"));
            String tieuDe = req.getParameter("tieuDe");
            String noiDung = req.getParameter("noiDung");
            String lienKet = req.getParameter("lienKet");
            int danhMucId = Integer.parseInt(req.getParameter("maDM"));

            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setMaDM(danhMucId);

            TinTuc tinTuc = new TinTuc(id, tieuDe, noiDung, lienKet, danhMuc);
            tinTucDAO.updateTinTuc(tinTuc);
        } else if ("delete".equals(action)) {
            int maTT = Integer.parseInt(req.getParameter("maTT"));
            tinTucDAO.deleteTinTuc(maTT);
        }
        resp.sendRedirect(req.getContextPath() + "/tintuc");
    }
}
