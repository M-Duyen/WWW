package iuh.fit.ongk_crud_danhmuc.servlet;

import iuh.fit.ongk_crud_danhmuc.dao.DanhMucDAO;
import iuh.fit.ongk_crud_danhmuc.dao.TinTucDAO;
import iuh.fit.ongk_crud_danhmuc.model.DanhMuc;
import iuh.fit.ongk_crud_danhmuc.model.TinTuc;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@WebServlet(name = "TinTucServlet", value = "/tintuc")
public class TinTucServlet extends HttpServlet {
    private TinTucDAO tinTucDAO;
    private DanhMucDAO danhMucDAO;

    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("DataSource is null");
        }
        tinTucDAO = new TinTucDAO(dataSource);
        danhMucDAO = new DanhMucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("form".equals(action)) {
            List<DanhMuc> danhMucs = danhMucDAO.getAllDanhMuc();
            req.setAttribute("danhMucs", danhMucs);

            String idStr = req.getParameter("id");

            if (idStr != null ) {
                int id = Integer.parseInt(idStr);
                TinTuc tinTuc = tinTucDAO.getTinTucById(id);
                req.setAttribute("tinTuc", tinTuc);

            }
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
            String noiDungTT = req.getParameter("noiDung");
            String lienKet = req.getParameter("lienKet");

            int maDM = Integer.parseInt(req.getParameter("danhMuc"));
            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setMaDM(maDM);

            TinTuc tinTuc = new TinTuc();
            tinTuc.setTieuDe(tieuDe);
            tinTuc.setNoiDungTT(noiDungTT);
            tinTuc.setLienKet(lienKet);
            tinTuc.setDanhMuc(danhMuc);

            tinTucDAO.addTinTuc(tinTuc);
        }
        resp.sendRedirect(req.getContextPath() + "/tintuc");
    }
}
