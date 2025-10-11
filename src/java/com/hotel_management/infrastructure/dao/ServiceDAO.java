package com.hotel_management.infrastructure.dao;

import com.hotel_management.domain.entity.Guest;
import com.hotel_management.domain.entity.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServiceDAO extends BaseDAO<Service>{

    public ServiceDAO(DataSource ds) {
        super(ds);
    }

    @Override
    public Service mapRow(ResultSet rs) throws SQLException {
        return new Service(
                rs.getInt("ServiceID"),
                rs.getString("ServiceName"),
                rs.getString("ServiceType"),
                rs.getDouble("Price")
        );
    }

    public List<Service> findAll() {
        return query("SELECT \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, S.Price\n" +
                "FROM SERVICE S");
    }

    public Optional<Service> findById(int id) {
        List<Service> services = query("SELECT \n" +
                "S.ServiceID, S.ServiceName, S.ServiceType, S.Price\n" +
                "FROM SERVICE S\n" +
                "WHERE S.ServiceID = ?", id);
        return services.stream().findFirst();
    }

}
