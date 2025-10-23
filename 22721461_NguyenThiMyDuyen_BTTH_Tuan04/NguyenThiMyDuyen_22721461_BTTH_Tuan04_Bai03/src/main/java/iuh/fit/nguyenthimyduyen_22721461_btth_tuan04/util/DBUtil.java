package iuh.fit.nguyenthimyduyen_22721461_btth_tuan04.util;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;

@AllArgsConstructor
public class DBUtil {
    private DataSource dataSource;

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
