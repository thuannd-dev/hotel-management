package com.hotel_management.infrastructure.provider;

import lombok.Getter;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author thuannd.dev
 */
public class DataSourceProvider {
    @Getter//create a static getter for dataSource
    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
        } catch (Exception e) {
            throw new RuntimeException("DataSource Not Founnd!", e);
        }
    }

}
