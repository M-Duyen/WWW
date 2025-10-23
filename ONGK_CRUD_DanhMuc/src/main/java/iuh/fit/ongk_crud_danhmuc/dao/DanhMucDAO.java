package iuh.fit.ongk_crud_danhmuc.dao;

import iuh.fit.ongk_crud_danhmuc.model.DanhMuc;
import iuh.fit.ongk_crud_danhmuc.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO implements DanhMucInterface {
    private final DBUtil dbUtil;

    public DanhMucDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    @Override
    public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                int maDM = rs.getInt("MADM");
                String tenDM = rs.getString("TENDANHMUC");
                String nguoiQuanLy = rs.getString("NGUOIQUANLY");
                String ghiChu = rs.getString("GHICHU");

                list.add(new DanhMuc(maDM, tenDM, nguoiQuanLy, ghiChu));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public DanhMuc getDanhMucById(int id) {
        String sql = "SELECT * FROM danhmuc WHERE MADM =? ";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int maDM = rs.getInt("MADM");
                    String tenDM = rs.getString("TENDANHMUC");
                    String nguoiQuanLy = rs.getString("NGUOIQUANLY");
                    String ghiChu = rs.getString("GHICHU");
                    return new DanhMuc(maDM, tenDM, nguoiQuanLy, ghiChu);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addDanhMuc(DanhMuc danhMuc) {
        String sql = "INSERT INTO danhmuc (TENDANHMUC, NGUOIQUANLY, GHICHU) VALUES (?,?,?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, danhMuc.getTenDanhMuc());
            ps.setString(2, danhMuc.getNguoiQuanLy());
            ps.setString(3, danhMuc.getGhiChu());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDanhMuc(DanhMuc danhMuc) {
        String sql = "UPDATE danhmuc SET TENDANHMUC = ?, NGUOIQUANLY = ?, GHICHU = ? WHERE MADM = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, danhMuc.getTenDanhMuc());
            ps.setString(2, danhMuc.getNguoiQuanLy());
            ps.setString(3, danhMuc.getGhiChu());

            ps.setInt(4, danhMuc.getMaDM());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteDanhMuc(int id){
        String sql = "DELETE FROM danhmuc WHERE MADM = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            int rows =  ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
    }
    @Override
    public List<DanhMuc> searchDanhMuc(String value){
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM danhmuc WHERE TENDANHMUC LIKE ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ){
            ps.setString(1, "%" + value + "%");
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    list.add(new DanhMuc(
                            rs.getInt("MADM"),
                            rs.getString("TENDANHMUC"),
                            rs.getString("NGUOIQUANLY"),
                            rs.getString("GHICHU")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
