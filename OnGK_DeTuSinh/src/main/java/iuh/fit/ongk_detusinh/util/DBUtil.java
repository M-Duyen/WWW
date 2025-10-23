package iuh.fit.ongk_detusinh.util;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@AllArgsConstructor
public class DBUtil {
    private DataSource dataSource;

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
