package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import repository.custom.CustomerRepository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean create(Customer customer) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");

            psTm.setString(1,customer.getId());
            psTm.setString(2,customer.getTitle());
            psTm.setString(3,customer.getName());
            psTm.setObject(4,customer.getDob());
            psTm.setDouble(5,customer.getSalary());
            psTm.setString(6,customer.getAddress());
            psTm.setString(7,customer.getCity());
            psTm.setString(8,customer.getProvince());
            psTm.setString(9,customer.getPostalCode());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public Customer getById(String id) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }
}
