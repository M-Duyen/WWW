package com.example.nguyenthimyduyen_22721461_btth_tuan04_bai04.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;

@NoArgsConstructor
@AllArgsConstructor
public class DBUtil {
    private DataSource dataSource;

    public Connection connection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return connection;
    }

}
