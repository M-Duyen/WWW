package iuh.fit.ongk_crud_danhmuc.dao;

import iuh.fit.ongk_crud_danhmuc.model.DanhMuc;
import iuh.fit.ongk_crud_danhmuc.model.TinTuc;
import iuh.fit.ongk_crud_danhmuc.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TinTucDAO {
    private final DBUtil dbUtil;

    public TinTucDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<TinTuc> getAllTinTuc() {
        List<TinTuc> list = new ArrayList<TinTuc>();
        String sql = "SELECT * FROM tintuc";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("MATT"));
                String tieuDe = rs.getString("TIEUDE");
                String noidungtt = rs.getString("NOIDUNGTT");
                String lienket = rs.getString("LIENKET");
                String maDM = rs.getString("MADM");

                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDM(Integer.parseInt(maDM));

                list.add(new TinTuc(id, tieuDe, noidungtt, lienket, danhMuc));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<TinTuc> getTinTucByDanhMucID(int danhMucID) {
        List<TinTuc> list = new ArrayList<>();
        String sql = "SELECT * FROM tintuc WHERE MADM = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, danhMucID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("MATT");
                    String tieuDe = rs.getString("TIEUDE");
                    String noiDung = rs.getString("NOIDUNGTT");
                    String lienKet = rs.getString("LIENKET");
                    int maDM = rs.getInt("MADM");

                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDM(maDM);

                    list.add(new TinTuc(id, tieuDe, noiDung, lienKet, danhMuc));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public TinTuc getTinTucById(int id) {
        String sql = "SELECT * FROM tintuc WHERE MATT = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String tieuDe = rs.getString("TIEUDE");
                    String noiDung = rs.getString("NOIDUNGTT");
                    String lienKet = rs.getString("LIENKET");
                    int maDM = rs.getInt("MADM");
                    DanhMuc danhMuc = new DanhMuc();
                    danhMuc.setMaDM(maDM);

                    TinTuc tinTuc = new TinTuc();
                    tinTuc.setId(id);
                    tinTuc.setTieuDe(tieuDe);
                    tinTuc.setNoiDungTT(noiDung);
                    tinTuc.setLienKet(lienKet);
                    tinTuc.setDanhMuc(danhMuc);

                    return tinTuc;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addTinTuc(TinTuc tinTuc) {
        String sql = "INSERT INTO tintuc (TIEUDE, NOIDUNGTT, LIENKET, MADM) VALUES (?,?,?,?)";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, tinTuc.getTieuDe());
            ps.setString(2, tinTuc.getNoiDungTT());
            ps.setString(3, tinTuc.getLienKet());
            ps.setInt(4, tinTuc.getDanhMuc().getMaDM());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
