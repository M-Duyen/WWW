package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.dao;

import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.beans.Product;
import iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.util.DBUtil;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mariadb://127.0.0.1:3306/shopdb");
        dataSource.setUsername("root");
        dataSource.setPassword("123456789");
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");

        // Initialize ProductDAO
        ProductDAO productDAO = new ProductDAO(dataSource);

        // Fetch and print all products
        List<Product> products = productDAO.getAllProducts();
//        System.out.println("List of Products:");
//        products.forEach(System.out::println);
        System.out.println(products);

    }
}
