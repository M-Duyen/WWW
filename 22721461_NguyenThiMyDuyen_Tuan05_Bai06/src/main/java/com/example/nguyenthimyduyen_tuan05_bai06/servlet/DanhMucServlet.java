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
@WebServlet(name = "DanhMucServlet", value = "/danhmuc")
public class DanhMucServlet extends HttpServlet {
    private DanhMucDAO danhMucDAO;
    private TinTucDAO tinTucDAO;

    @Resource(name = "jdbc/quanlydanhmuc")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        if(dataSource == null){
            throw  new ServletException("Datasource is null");
        }
        danhMucDAO = new DanhMucDAO(dataSource);
        tinTucDAO = new TinTucDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("list".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            List<TinTuc> tinTucs = tinTucDAO.getTinTucByDanhMucID(id);
            req.setAttribute("tinTucs", tinTucs);
            req.getRequestDispatcher("/tinTuc-list.jsp").forward(req, resp);
        }
       else {
            List<DanhMuc> danhMucs = danhMucDAO.getAllDanhMuc();
            req.setAttribute("danhMucs", danhMucs);
            req.getRequestDispatcher("/danhMuc-list.jsp").forward(req,resp);
        }
    }
}
