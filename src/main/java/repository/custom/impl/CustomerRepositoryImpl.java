package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import model.tm.CustomerTM;
import repository.custom.CustomerRepository;
import util.CrudUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public boolean create(Customer customer) {
        try {
            return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)",
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Customer customer) {
        try {
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

            return CrudUtil.execute("update customer SET CustTitle=?,CustName=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID =?",
                    customerTM.getTitle(),
                    customerTM.getFullName(),
                    customerTM.getDob().toString(),
                    customerTM.getSalary(),
                    customerTM.getAddress(),
                    customerTM.getCity(),
                    customerTM.getProvince(),
                    customerTM.getPostalCode(),
                    customerTM.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {
           return CrudUtil.execute("DELETE FROM customer WHERE CustID = ?",id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer getById(String id) {
        try {


            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE CustID = ?", id);

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

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");

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
