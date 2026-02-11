package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import model.tm.CustomerTM;
import repository.custom.CustomerRepository;


import java.sql.*;
import java.util.ArrayList;
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
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("update customer SET CustTitle=?,CustName=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID =?");

            CustomerTM customerTM = new CustomerTM(
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );

            psTm.setString(1,customerTM.getTitle());
            psTm.setString(2,customerTM.getFullName());
            psTm.setString(3,customerTM.getDob().toString());
            psTm.setDouble(4,customerTM.getSalary());
            psTm.setString(5,customerTM.getAddress());
            psTm.setString(6,customerTM.getCity());
            psTm.setString(7,customerTM.getProvince());
            psTm.setString(8,customerTM.getPostalCode());
            psTm.setString(9,customerTM.getId());

            return  psTm.executeUpdate()>0;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM customer WHERE CustID = ?");
            psTm.setString(1,id);

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM customer WHERE CustID = ?");

            psTm.setString(1,id);
            ResultSet resultSet = psTm.executeQuery();

            resultSet.next();


            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

            ArrayList<Customer> customerTMS = new ArrayList<>();

            while (resultSet.next()){
                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)

                );
                customerTMS.add(customer);
            }
            return customerTMS;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
