package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import model.tm.ItemTM;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colPackSize;

    @FXML
    private TableColumn colQtyOnHand;

    @FXML
    private TableColumn colUnitePrice;

    @FXML
    private TableView tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    void btnAddItemOnAction(ActionEvent event) throws SQLException {
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code, description, packSize, unitPrice, qtyOnHand);
        System.out.println(item);


        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement psTm = connection.prepareStatement("INSERT INTO Item VALUES (?, ?, ?, ?, ?)");

        psTm.setString(1, item.getCode());
        psTm.setString(2, item.getDescription());
        psTm.setString(3, item.getPackSize());
        psTm.setDouble(4, item.getUnitPrice());
        psTm.setInt(5, item.getQtyOnHand());

        if (psTm.executeUpdate()>0){
            new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully").show();
            loadItems();
        }else {
            new Alert(Alert.AlertType.WARNING,"Item Not Added").show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("DELETE FROM Item WHERE ItemCode=?");
            psTm.setString(1,txtItemCode.getText());
            if (psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted Successfully").show();
                loadItems();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Item Not Deleted").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnLoadItemOnAction(ActionEvent event) {
        loadItems();
    }
    public void loadItems()  {

        ItemServiceImpl itemService = new ItemServiceImpl();
        List<Item> all = itemService.getAll();

        ArrayList<ItemTM> itemTMArrayList = new ArrayList<>();
        all.forEach(item -> {
            itemTMArrayList.add(new ItemTM(
                    item.getCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            ));
        });

        tblItem.setItems(FXCollections.observableArrayList(itemTMArrayList));

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("SELECT * FROM Item WHERE ItemCode=?");
            psTm.setString(1,txtItemCode.getText());
            ResultSet resultSet = psTm.executeQuery();

            resultSet.next();

            ItemTM itemTM = new ItemTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
            setTextToValues(itemTM);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void setTextToValues(ItemTM item) {
        txtItemCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtPackSize.setText(item.getPackSize());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
    }

    public void clearFields(){
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

            PreparedStatement psTm = connection.prepareStatement("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
            psTm.setString(1,txtDescription.getText());
            psTm.setString(2,txtPackSize.getText());
            psTm.setDouble(3,Double.parseDouble(txtUnitPrice.getText()));
            psTm.setInt(4,Integer.parseInt(txtQtyOnHand.getText()));
            psTm.setString(5,txtItemCode.getText());
            if (psTm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated Successfully").show();
                loadItems();
            }else {
                new Alert(Alert.AlertType.WARNING,"Item Not Updated").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItems();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitePrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));



        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{

            System.out.println("New Value : "+newValue);

        assert newValue != null;

        setTextToValues((ItemTM)newValue);
        });


    }
}
