package model;

import repository.CrudRepository;

import java.util.List;

public class Order implements CrudRepository<Order, String> {
    @Override
    public boolean create(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public Order getById(String s) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return List.of();
    }
}
