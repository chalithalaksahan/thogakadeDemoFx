package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerFormController {

        @FXML
        private TableColumn colAddress;

        @FXML
        private TableColumn colId;

        @FXML
        private TableColumn colName;

        @FXML
        private TableColumn colSalary;

        @FXML
        private JFXTextField txtAddress;

        @FXML
        private JFXTextField txtId;

        @FXML
        private JFXTextField txtName;

        @FXML
        private JFXTextField txtSalary;

        @FXML
        void btnAddCustomerOnAction(ActionEvent event) {

        }

        @FXML
        void btnLoadCustomerOnAction(ActionEvent event) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "root");
                System.out.println(connection);

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");

                ArrayList<Customer> customerArrayList = new ArrayList<>();

                while(resultSet.next()) {
                   customerArrayList.add(
                           new Customer(
                                   resultSet.getString(1),
                                   resultSet.getString(2),
                                   resultSet.getString(3),
                                   resultSet.getDouble(4)
                           )
                   );
                }
                System.out.println(customerArrayList);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

}

