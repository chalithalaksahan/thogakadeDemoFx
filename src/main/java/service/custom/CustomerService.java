package service.custom;

import model.Customer;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService extends SuperService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
    Customer searchCustomerById(String id) throws SQLException;
    List<Customer> getAll() throws SQLException;
    List<String> getAllCustomerIDs() throws SQLException;
}
