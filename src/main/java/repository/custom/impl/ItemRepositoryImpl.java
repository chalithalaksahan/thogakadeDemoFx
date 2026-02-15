package repository.custom.impl;

import db.DBConnection;
import model.Item;
import repository.custom.ItemRepository;
import util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    @Override
    public boolean create(Item item) {
        try {
           return CrudUtil.execute("INSERT INTO Item VALUES (?, ?, ?, ?, ?)",
                    item.getCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getStock()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Item item) {
        try {
           return CrudUtil.execute("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, Stock=? WHERE ItemCode=?",
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getStock(),
                    item.getCode()
            );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        try {

          return CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?",id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item getById(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", id);

            resultSet.next();

            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> getAll() {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");

            ArrayList<Item> itemArrayList = new ArrayList<>();

            while (resultSet.next()){
                Item item =  new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)

                );
                itemArrayList.add(item);
            }
            return itemArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
