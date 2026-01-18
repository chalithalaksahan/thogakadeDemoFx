package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements  ItemService{
    @Override
    public boolean addItem(Item item) {
        return false;
    }

    @Override
    public boolean updateItem(Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public Item searchById(String id) {
        return null;
    }

    @Override
    public List<Item> getAll() {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Item");

            ArrayList<Item> itemArrayList = new ArrayList<>();

            while (resultSet.next()){
                 Item itemTM =  new Item(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getDouble(4),
                                resultSet.getInt(5)

                );
                 itemArrayList.add(itemTM);
            }
           return itemArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
