package iuh.fit.ongk_crud_danhmuc.servlet;

import iuh.fit.ongk_crud_danhmuc.dao.DanhMucDAO;
import iuh.fit.ongk_crud_danhmuc.dao.DanhMucInterface;
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
@WebServlet(name = "DanhMucServlet", value = "/danhmuc")
public class DanhMucServlet extends HttpServlet{
    private DanhMucInterface danhMucInterface;
    private TinTucDAO tinTucDAO;

    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if (dataSource == null) {
            throw new ServletException("Datasource is null");
        }
        danhMucInterface = new DanhMucDAO(dataSource);
        tinTucDAO = new TinTucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(("action"));
        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("maDM"));
            DanhMuc danhMuc = danhMucInterface.getDanhMucById(id);
            req.setAttribute("danhMuc", danhMuc);
            req.getRequestDispatcher("/danhMuc-form.jsp").forward(req, resp);
        } else if ("search".equals(action)) {
            String value = req.getParameter("search");
            List<DanhMuc> danhMucs;
            if (value != null && !value.trim().isEmpty()) {
                danhMucs = danhMucInterface.searchDanhMuc(value);
                req.setAttribute("danhMucs", danhMucs);
                req.getRequestDispatcher("/danhMuc-list.jsp").forward(req, resp);
            }
        } else if ("listTinTuc".equals(action)) {
            int maDM = Integer.parseInt(req.getParameter("maDM"));
            List<TinTuc> tinTucs = tinTucDAO.getTinTucByDanhMucID(maDM);
            req.setAttribute("tinTucs", tinTucs);
            req.getRequestDispatcher("/tinTuc-list.jsp").forward(req,resp);

        } else {
            // Mặc định: hiển thị tất cả danh mục
            List<DanhMuc> danhMucs = danhMucInterface.getAllDanhMuc();
            req.setAttribute("danhMucs", danhMucs);
            req.getRequestDispatcher("/danhMuc-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("maDM"));
            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setTenDanhMuc(req.getParameter("tenDanhMuc"));
            danhMuc.setNguoiQuanLy(req.getParameter("nguoiQuanLy"));
            danhMuc.setGhiChu(req.getParameter("ghiChu"));
            danhMuc.setMaDM(id);

            danhMucInterface.updateDanhMuc(danhMuc);
        } else if ("add".equals(action)) {
            DanhMuc danhMuc = new DanhMuc();
            danhMuc.setTenDanhMuc(req.getParameter("tenDanhMuc"));
            danhMuc.setNguoiQuanLy(req.getParameter("nguoiQuanLy"));
            danhMuc.setGhiChu(req.getParameter("ghiChu"));

            danhMucInterface.addDanhMuc(danhMuc);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("maDM"));
            boolean check = danhMucInterface.deleteDanhMuc(id);
            if(!check){
                req.setAttribute("error", "Khong the xoa vi danh muc dang su dung");
                List<DanhMuc> danhMucs = danhMucInterface.getAllDanhMuc();
                req.setAttribute("danhMucs", danhMucs);
                req.getRequestDispatcher("/danhMuc-list.jsp").forward(req, resp);
                return; // dừng hẳn, không redirect nữa
            }
        }
        resp.sendRedirect(req.getContextPath() + "/danhmuc");
    }

}

