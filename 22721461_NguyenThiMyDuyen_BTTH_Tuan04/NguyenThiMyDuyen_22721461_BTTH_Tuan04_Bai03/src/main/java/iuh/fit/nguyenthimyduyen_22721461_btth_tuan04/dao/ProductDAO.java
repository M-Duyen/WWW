package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.dao;

import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans.Product;
import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final DBUtil dbUtil;

    public ProductDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Product> getAllProducts(){
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = dbUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()){
                int id = rs.getInt("id");
                String model = rs.getString("model");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("descrip");
                String image = rs.getString("image");

                Product product = new Product(id,model,price,quantity,description,image);
                list.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public Product getProductByID(int id){
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = dbUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int proID = rs.getInt("id");
                    String model = rs.getString("model");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String description = rs.getString("descrip");
                    String image = rs.getString("image");

                    return new Product(proID,model,price,quantity,description,image);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }

}
