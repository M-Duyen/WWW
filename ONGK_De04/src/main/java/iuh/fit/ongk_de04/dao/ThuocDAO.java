package iuh.fit.ongk_de04.dao;

import iuh.fit.ongk_de04.model.LoaiThuoc;
import iuh.fit.ongk_de04.model.Thuoc;
import iuh.fit.ongk_de04.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThuocDAO {
    private final DBUtil dbUtil;

    public ThuocDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Thuoc> getAllThuoc() {
        List<Thuoc> thuocs = new ArrayList<>();
        String sql = "SELECT * FROM thuoc";
        try (Connection connection = dbUtil.getCOnnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                int id = rs.getInt("maThuoc");
                String ten = rs.getString("tenthuoc");
                double gia = rs.getDouble("gia");
                int nam = rs.getInt("namSX");

                int maLoai = rs.getInt("maLoai");
                LoaiThuoc loaiThuoc = new LoaiThuoc();
                loaiThuoc.setMaLoai(maLoai);

                thuocs.add(new Thuoc(id, ten, gia, nam, loaiThuoc));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return thuocs;
    }
    public List<Thuoc> getThuocByLoaiThuocId(int loaiThuocId){
        List<Thuoc> thuocs = new ArrayList<>();
        String sql = "SELECT * FROM thuoc WHERE maLoai = ?";
        try(Connection connection = dbUtil.getCOnnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, loaiThuocId);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int id = rs.getInt("maThuoc");
                    String ten = rs.getString("tenthuoc");
                    double gia = rs.getDouble("gia");
                    int nam = rs.getInt("namSX");

                    int maLoai = rs.getInt("maLoai");
                    LoaiThuoc loaiThuoc = new LoaiThuoc();
                    loaiThuoc.setMaLoai(maLoai);

                    thuocs.add(new Thuoc(id, ten, gia, nam, loaiThuoc));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return thuocs;
    }

    public void addThuoc(Thuoc thuoc){
        String sql = "INSERT INTO thuoc (tenthuoc, gia, namSX, maLoai) VALUES (?,?,?,?)";
        try(Connection connection = dbUtil.getCOnnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, thuoc.getTenThuoc());
            ps.setDouble(2, thuoc.getGia());
            ps.setInt(3, thuoc.getNamSX());
            ps.setInt(4, thuoc.getLoaiThuoc().getMaLoai());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
