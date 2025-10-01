/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.hotel_management.presentation.controller;

import com.hotel_management.application.service.StaffService;
import com.hotel_management.infrastructure.dao.StaffDAO;
import com.hotel_management.infrastructure.provider.DataSourceProvider;
import com.hotel_management.presentation.dto.staff.StaffViewModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author DELL
 */
public class LoginController extends HttpServlet {

    private StaffDAO staffDAO;
    private StaffService staffService;

    @Override
    public void init() throws ServletException {
        DataSource ds = DataSourceProvider.getDataSource();
        this.staffDAO = new StaffDAO(ds);
        this.staffService = new StaffService(staffDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        List<StaffViewModel> s = staffService.getAllStaff();
        PrintWriter out = response.getWriter();
        for (StaffViewModel staff : s) {
            out.println(staff.getStaffid() + " - " + staff.getFullname() + " - " + staff.getRole());
        }
    }
}
