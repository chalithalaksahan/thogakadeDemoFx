package controller.item;

import com.jfoenix.controls.JFXTextField;
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
import service.ServiceFactory;
import service.custom.ItemService;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
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
    public TableColumn colStock;

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

    ItemService serviceType=ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);


    @FXML
    void btnAddItemOnAction(ActionEvent event){
        String code = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int stock = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code, description, packSize, unitPrice, stock);


        try {
            if ( serviceType.addItem(item)){
                new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully").show();
                loadItems();
            }else {
                new Alert(Alert.AlertType.WARNING,"Item Not Added").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {


        if (serviceType.deleteItem(txtItemCode.getText())){
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


        try {
            List<Item>   all = serviceType.getAllItems();

            ArrayList<ItemTM> itemTMArrayList = new ArrayList<>();
            all.forEach(item ->
                    itemTMArrayList.add(new ItemTM(
                            item.getCode(),
                            item.getDescription(),
                            item.getPackSize(),
                            item.getUnitPrice(),
                            item.getStock()
                    )));

            tblItem.setItems(FXCollections.observableArrayList(itemTMArrayList));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {



        try {
            Item item = serviceType.getItemByCode(txtItemCode.getText());

            ItemTM itemTM = new ItemTM(
                    item.getCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getStock()
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
        txtQtyOnHand.setText(String.valueOf(item.getStock()));
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
        int stock = Integer.parseInt(txtQtyOnHand.getText());

        Item item = new Item(code, description, packSize, unitPrice, stock);

        if (serviceType.updateItem(item)){
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
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));



        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->{


            if (newValue != null) {  // ✓ Proper null check
                setTextToValues((ItemTM)newValue);
            } else {
                clearFields();  // Optional: clear fields when nothing selected
            }
        });


    }
}
