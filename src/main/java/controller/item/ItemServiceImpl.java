package controller.item;

import db.DBConnection;
import javafx.scene.control.Alert;
import model.Item;
import model.tm.ItemTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements  ItemService{
    @Override
    public boolean addItem(Item item) {

            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement psTm = connection.prepareStatement("INSERT INTO Item VALUES (?, ?, ?, ?, ?)");

                psTm.setString(1, item.getCode());
                psTm.setString(2, item.getDescription());
                psTm.setString(3, item.getPackSize());
                psTm.setDouble(4, item.getUnitPrice());
                psTm.setInt(5, item.getQtyOnHand());

                return psTm.executeUpdate() > 0;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public boolean updateItem(Item item) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
            psTm.setString(1,item.getDescription());
            psTm.setString(2,item.getPackSize());
            psTm.setDouble(3,item.getUnitPrice());
            psTm.setInt(4,item.getQtyOnHand());
            psTm.setString(5, item.getCode());

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM Item WHERE ItemCode=?");
            psTm.setString(1,id);

            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchById(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
            psTm.setString(1,id);
            ResultSet resultSet = psTm.executeQuery();

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
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Item");

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
