package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.sql.*;

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

                resultSet.next();

                System.out.println(resultSet.getString(1));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

}

