package service.custom.impl;

import model.Customer;
import repository.RepositoryFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceimpl implements CustomerService {

    CustomerRepository customerRepository = RepositoryFactory.getInstance().getRepositoryType(RepositoryType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        return customerRepository.create(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
     return customerRepository.update(customer);
    }

    @Override
    public boolean deleteCustomer(String id) {
       return  customerRepository.deleteById(id);
    }

    @Override
    public Customer searchCustomerById(String id) throws SQLException {
       return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getAll() throws SQLException {
       return customerRepository.getAll();
    }

    @Override
    public List<String> getAllCustomerIDs() throws SQLException {
        List<Customer> all = getAll();
        ArrayList<String> idList = new ArrayList<>();
        for (Customer customer : all) {
            idList.add(customer.getId());
        }
        return idList;
    }
}
