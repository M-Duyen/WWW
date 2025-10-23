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

@WebServlet(name = "LoaiThuocServlet", value = "/loaithuoc")
public class LoaiThuocServlet extends HttpServlet {
    private LoaiThuocDAO loaiThuocDAO;
    private ThuocDAO thuocDAO;

    @Resource(name = "jdbc/quanlythuoc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw new ServletException("DataSource is null");
        }
        loaiThuocDAO = new LoaiThuocDAO(dataSource);
        thuocDAO = new ThuocDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("getList".equals(action)){
            int id = Integer.parseInt(req.getParameter("maLoai"));
            List<Thuoc> thuocs = thuocDAO.getThuocByLoaiThuocId(id);
            req.setAttribute("thuocs", thuocs);
            req.getRequestDispatcher("/thuoc-list.jsp").forward(req, resp);
        }else{
            List<LoaiThuoc> loaiThuocs = loaiThuocDAO.getAllLoaiThuoc();
            req.setAttribute("loaiThuocs", loaiThuocs);
            req.getRequestDispatcher("/loaiThuoc-list.jsp").forward(req,resp);
        }


    }
}
