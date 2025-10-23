package com.example.nguyenthimyduyen_tuan05_bai06.dao;

import com.example.nguyenthimyduyen_tuan05_bai06.model.DanhMuc;
import com.example.nguyenthimyduyen_tuan05_bai06.model.TinTuc;
import com.example.nguyenthimyduyen_tuan05_bai06.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TinTucDAO {
    private final DBUtil dbUtil;


    public TinTucDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<TinTuc> getAllTinTuc(){
        List<TinTuc> tinTucs = new ArrayList<>();
        String sql = "SELECT * FROM tintuc";
        try(Connection connection = dbUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
        ){
            while (rs.next()){
                int id = rs.getInt("MATT");
                String tieuDe = rs.getString("TIEUDE");
                String noiDung = rs.getString("NOIDUNGTT");
                String lienKet = rs.getString("LIENKET");
                int danhMucId = rs.getInt("MADM");

                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDM(danhMucId);

                TinTuc tinTuc = new TinTuc(id,tieuDe,noiDung,lienKet,danhMuc);
                tinTucs.add(tinTuc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tinTucs;
    }
    public List<TinTuc> getTinTucByDanhMucID(int id){
        List<TinTuc> list = new ArrayList<>();
        String sql = "SELECT * FROM tintuc WHERE MADM = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

        ){
            ps.setInt(1, id);
            try( ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int idStr = rs.getInt("MATT");
                    String tieuDe = rs.getString("TIEUDE");
                    String noiDung = rs.getString("NOIDUNGTT");
                    String lienKet = rs.getString("LIENKET");
                    int danhMucId = rs.getInt("MADM");

                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDM(danhMucId);

                    TinTuc tinTuc = new TinTuc(idStr,tieuDe,noiDung,lienKet,danhMuc);
                    list.add(tinTuc);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public TinTuc getByID(int id){
        String sql = "SELECT * FROM tintuc WHERE MATT = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1,id);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int idStr = rs.getInt("MATT");
                    String tieuDe = rs.getString("TIEUDE");
                    String noiDung = rs.getString("NOIDUNGTT");
                    String lienKet = rs.getString("LIENKET");
                    int danhMucId = rs.getInt("MADM");

                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDM(danhMucId);

                   return new TinTuc(idStr,tieuDe,noiDung,lienKet,danhMuc);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateTinTuc(TinTuc tinTuc){
        String sql = "UPDATE tintuc SET TIEUDE = ?, NOIDUNGTT = ?, LIENKET = ?, MADM = ? WHERE MATT = ?";
        try(Connection connection = dbUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, tinTuc.getTieuDe());
            ps.setString(2, tinTuc.getNoiDung());
            ps.setString(3, tinTuc.getLienKet());
            ps.setInt(4, tinTuc.getDanhMuc().getMaDM());
            ps.setInt(5, tinTuc.getMaTT());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTinTuc(int id){
        String sql = "DELETE FROM tintuc WHERE MATT = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void addTinTuc(TinTuc tinTuc){
        String sql = "INSERT INTO tintuc (TIEUDE, NOIDUNGTT, LIENKET, MADM) VALUES (?,?,?,?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setString(1, tinTuc.getTieuDe());
            ps.setString(2, tinTuc.getNoiDung());
            ps.setString(3, tinTuc.getLienKet());
            ps.setInt(4, tinTuc.getDanhMuc().getMaDM());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
