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
import model.Customer;
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

        ItemServiceImpl itemService = new ItemServiceImpl();


        if ( itemService.addItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully").show();
            loadItems();
        }else {
            new Alert(Alert.AlertType.WARNING,"Item Not Added").show();
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       ItemServiceImpl itemService = new ItemServiceImpl();

        if (itemService.deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted Successfully").show();
            loadItems();
            clearFields();
        }else {
            new Alert(Alert.AlertType.WARNING,"Item Not Deleted").show();
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
        ItemServiceImpl itemService = new ItemServiceImpl();

        Item item = itemService.searchById(txtItemCode.getText());

        ItemTM itemTM = new ItemTM(
                item.getCode(),
                item.getDescription(),
                item.getPackSize(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );

        setTextToValues(itemTM);


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

        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code, description, packSize, unitPrice, qtyOnHand);
        System.out.println(item);

        if (new ItemServiceImpl().updateItem(item)){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated Successfully").show();
                loadItems();
            }else {
                new Alert(Alert.AlertType.WARNING,"Item Not Updated").show();
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

            if (newValue != null) {  // ✓ Proper null check
                setTextToValues((ItemTM)newValue);
            } else {
                clearFields();  // Optional: clear fields when nothing selected
            }
        });


    }
}
