package service.custom.impl;

import model.Customer;
import repository.RepositoryFactory;
import repository.custom.CustomerRepository;
import service.custom.CustomerService;
import util.RepositoryType;

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
    public Customer searchCustomerById(String id) {
       return customerRepository.getById(id);
    }

    @Override
    public List<Customer> getAll() {
       return customerRepository.getAll();
    }
}
