package com.example.nguyenthimyduyen_tuan05_bai06.dao;

import com.example.nguyenthimyduyen_tuan05_bai06.model.DanhMuc;
import com.example.nguyenthimyduyen_tuan05_bai06.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {
    private final DBUtil dbUtil;

    public DanhMucDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<DanhMuc> getAllDanhMuc(){
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc";
        try(Connection connection = dbUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ){
            while (rs.next()){
                int id = rs.getInt("MADM");
                String ten = rs.getString("TENDANHMUC");
                String nguoiQuanLy = rs.getString("NGUOIQUANLY");
                String ghiChu = rs.getString("GHICHU");

                DanhMuc danhMuc = new DanhMuc(id,ten,nguoiQuanLy,ghiChu);
                list.add(danhMuc);
            }
        }catch (RuntimeException | SQLException e){
            throw new RuntimeException(e);
        }
        return list;
    }
}
