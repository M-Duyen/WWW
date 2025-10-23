package iuh.fit.ongk_de04.dao;

import iuh.fit.ongk_de04.model.LoaiThuoc;
import iuh.fit.ongk_de04.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoaiThuocDAO {
    private final DBUtil dbUtil;

    public LoaiThuocDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<LoaiThuoc> getAllLoaiThuoc() {
        List<LoaiThuoc> list = new ArrayList<>();
        String sql = "SELECT * FROM loaithuoc";
        try (Connection connection = dbUtil.getCOnnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {
            while (rs.next()) {
                int id = rs.getInt("maLoai");
                String ten = rs.getString("tenLoai");
                list.add(new LoaiThuoc(id, ten));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
