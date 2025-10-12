/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hotel_management.infrastructure.persistence.provider;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import lombok.Getter;

/**
 *
 * @author TR_NGHIA
 */

public class DataSourceProvider {
    @Getter
    private static DataSource dataSource;

    static {
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/HotelDB");
        } catch (Exception e) {
            throw new RuntimeException("DataSource Not Founnd!", e);
        }
    }
    
}
