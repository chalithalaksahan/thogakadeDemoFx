package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.tm.CustomerTM;
import service.custom.impl.CustomerServiceimpl;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXComboBox cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colDob;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPostalCode;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String title = cmbTitle.getValue().toString();
        LocalDate dob = dateDob.getValue();
        Double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        Customer customer = new Customer(id,title, name, dob, salary, address, city, province, postalCode);
        System.out.println(customer);

        if (new CustomerServiceimpl().addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added!").show();
            loadTable();
            clearFields();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Added!").show();
        }

    }

    @FXML
    void btnLoadCustomerOnAction(ActionEvent event) {
        loadTable();
    }
    public void loadTable(){
        CustomerServiceimpl customerService = new CustomerServiceimpl();
        List<Customer> all = customerService.getAll();

        ArrayList<CustomerTM> customerTMS = new ArrayList<>();

        all.forEach(customer ->
                customerTMS.add(new CustomerTM(
                                customer.getId(),
                                customer.getTitle(),
                                customer.getName(),
                                customer.getDob(),
                                customer.getSalary(),
                                customer.getAddress(),
                                customer.getCity(),
                                customer.getProvince(),
                                customer.getPostalCode()
                        )
                )
        );

        tblCustomer.setItems(FXCollections.observableArrayList(customerTMS));
    }
    @FXML
    public void btnUpdateOnAction(ActionEvent event) {

        CustomerServiceimpl customerService = new CustomerServiceimpl();

        String title = (String) cmbTitle.getValue();
        String name = txtName.getText();
        String dob = String.valueOf(dateDob.getValue());
        double salary = Double.parseDouble(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();
        String id = txtId.getText();

        Customer customer = new Customer(id,title, name, LocalDate.parse(dob), salary, address, city, province, postalCode);
        if (customerService.updateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
            loadTable();
            clearFields();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated!").show();
        }


    }
    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        CustomerServiceimpl customerService = new CustomerServiceimpl();

        if(customerService.deleteCustomer(txtId.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
            loadTable();
            clearFields();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
        }
    }
    @FXML
    public void btnSearchOnAction(ActionEvent actionEvent) {
        CustomerServiceimpl customerService = new CustomerServiceimpl();

        Customer customer = customerService.searchCustomerById(txtId.getText());

        CustomerTM customerTM = new CustomerTM(
                customer.getId(),
                customer.getTitle(),
                customer.getName(),
                customer.getDob(),
                customer.getSalary(),
                customer.getAddress(),
                customer.getCity(),
                customer.getProvince(),
                customer.getPostalCode()
        );

        setTextToValues(customerTM);
    }

    public void setTextToValues(CustomerTM customer){
        txtId.setText(customer.getId());
        cmbTitle.setValue(customer.getTitle());
        txtName.setText(customer.getName());
        dateDob.setValue(customer.getDob());
        txtSalary.setText(customer.getSalary().toString());
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());

    }

    public void clearFields(){
        txtId.setText("");
        cmbTitle.setValue(null);
        txtName.setText("");
        dateDob.setValue(null);
        txtSalary.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtProvince.setText("");
        txtPostalCode.setText("");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        cmbTitle.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList("Mr","Miss","Ms")
                )
        );
        loadTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue!= null) {
                setTextToValues((CustomerTM) newValue);
            } else {
                clearFields();  // Optional: clear fields when nothing selected
            }
        });
    }


}
