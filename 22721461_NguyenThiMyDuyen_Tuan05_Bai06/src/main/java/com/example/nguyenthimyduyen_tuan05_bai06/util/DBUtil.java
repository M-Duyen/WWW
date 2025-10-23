package com.example.nguyenthimyduyen_tuan05_bai06.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;

@NoArgsConstructor
@AllArgsConstructor
public class DBUtil {
    private DataSource dataSource;
    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return connection;
    }

}
