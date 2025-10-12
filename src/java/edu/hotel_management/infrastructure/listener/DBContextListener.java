package edu.hotel_management.infrastructure.listener;

import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

/**
 *
 * @author TR_NGHIA
 */

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        
        // --- CẤU HÌNH DATABASE CONNECTION POOL (DBCP) ---
        BasicDataSource ds = new BasicDataSource();
        
        // !!! QUAN TRỌNG: Thay thế bằng thông tin kết nối thực tế của bạn !!!
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        // Thay "YourDatabaseName" bằng tên database của bạn
        ds.setUrl("jdbc:sqlserver://localhost:1433;databaseName=YourDatabaseName;encrypt=true;trustServerCertificate=true;"); 
        // Thay "your_username" bằng tên người dùng SQL Server
        ds.setUsername("your_username");
        // Thay "your_password" bằng mật khẩu
        ds.setPassword("your_password");

        // Cấu hình các thuộc tính của Pool (tùy chọn nhưng nên có)
        ds.setMinIdle(5); // Số kết nối tối thiểu luôn duy trì
        ds.setMaxIdle(10); // Số kết nối tối đa ở trạng thái chờ
        ds.setMaxOpenPreparedStatements(100);

        // Lưu DataSource vào ServletContext để toàn bộ ứng dụng có thể truy cập
        context.setAttribute("DBCP_DATASOURCE", ds);
        
        System.out.println(">>> Database Connection Pool has been initialized and set in ServletContext.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        BasicDataSource ds = (BasicDataSource) context.getAttribute("DBCP_DATASOURCE");
        try {
            if (ds != null) {
                // Đóng connection pool để giải phóng tài nguyên
                ds.close();
                System.out.println(">>> Database Connection Pool has been closed.");
            }
        } catch (SQLException e) {
            // Ghi lại lỗi nếu có vấn đề khi đóng pool
            e.printStackTrace();
        }
    }
}