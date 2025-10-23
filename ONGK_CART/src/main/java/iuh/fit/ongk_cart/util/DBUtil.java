package iuh.fit.ongk_cart.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
public class DBUtil {
    private DataSource dataSource;

    public Connection getCOnnection(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }
}
