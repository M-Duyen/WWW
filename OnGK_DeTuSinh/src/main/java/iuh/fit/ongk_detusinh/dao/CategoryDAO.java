package iuh.fit.ongk_detusinh.dao;

import iuh.fit.ongk_detusinh.model.Category;
import iuh.fit.ongk_detusinh.util.DBUtil;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements CategoryImpl {
    private final DBUtil dbUtil;

    public CategoryDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    @Override
    public List<Category> getAllCategory(){
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (Connection connection = dbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ){
            while (rs.next()){
                list.add(new Category(
                        rs.getString("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Category getCategoryById(String id){
        String sql = "SELECT * FROM category WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Category(rs.getString("id"), rs.getString("name"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void addCategory(Category category){
        String sql = "INSERT INTO category (id, name) VALUES (?,?)";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, category.getId());
            ps.setString(2, category.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCategory(Category category){
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCategory(String id){
        String sql = "DELETE FROM category WHERE id = ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, id);

            int rows = ps.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            if(e.getErrorCode() == 1451){
                return false;
            }
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Category> getCategoryByName(String categoryName){
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM category WHERE name LIKE ?";
        try(Connection connection = dbUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, "%"+categoryName+"%");
            try(ResultSet rs = ps.executeQuery()){
               while (rs.next()){
                   list.add(new Category(rs.getString("id"), rs.getString("name")));
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
