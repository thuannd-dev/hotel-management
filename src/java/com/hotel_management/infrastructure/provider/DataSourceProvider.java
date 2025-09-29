/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hotel_management.infrastructure.provider;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author DELL
 */
public class DataSourceProvider {
    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDB");
        } catch (Exception e) {
            throw new RuntimeException("DataSource Not Founnd!", e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
