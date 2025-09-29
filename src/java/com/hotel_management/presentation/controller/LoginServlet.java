/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.domain.entity.Staff;
import com.hotel_management.infrastructure.dao.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author DELL
 */
public class LoginServlet extends HttpServlet {

    private DataSource ds;

    @Override
    public void init() throws ServletException {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            ds = (DataSource) envCtx.lookup("jdbc/MyDB");
        } catch (NamingException e) {
            throw new ServletException("Không tìm thấy DataSource!", e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
//        String sql = "select StaffID,FullName,Role,Username,PasswordHash,Phone,Email\n"
//                        + "from dbo.STAFF\n";
//        try (Connection conn = ds.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql);
//             ResultSet rs = ps.executeQuery()) {
//
//            PrintWriter out = response.getWriter();
//            while (rs.next()) {
//                out.println(rs.getInt("StaffID") + " - " + rs.getString("FullName"));
//            }
//        } catch (SQLException e) {
//            throw new ServletException("Lỗi khi query DB", e);
//        }
        StaffDAO dao = new StaffDAO();
        List<Staff> s = dao.findAll();
        PrintWriter out = response.getWriter();
        for (Staff staff : s) {
            out.println(staff.getStaffid() + " - " + staff.getFullname());
        }
    }
}
