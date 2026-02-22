package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane dashRoot;

    private Injector injector;


    @FXML
    void btnCustomerFormOnActiom(MouseEvent event) {
        try {

        URL resource = this.getClass().getResource("/view/customer_form.fxml");

        assert resource != null;

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setControllerFactory(injector::getInstance);

            Parent  parent = fxmlLoader.load();

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnOrderFormOnActiom(MouseEvent event) {
        try {

            URL resource = this.getClass().getResource("/view/order_form.fxml");

            assert resource != null;

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(injector::getInstance);

            Parent parent = fxmlLoader.load();

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnItemFormOnActiom(MouseEvent event) {
        try {

            URL resource = this.getClass().getResource("/view/item_form.fxml");

            assert resource != null;

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(injector::getInstance);

            Parent   parent = fxmlLoader.load();

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());

        try {

            URL resource = this.getClass().getResource("/view/customer_form.fxml");

            assert resource != null;

            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(injector::getInstance);

            Parent   parent = fxmlLoader.load() ;

            dashRoot.getChildren().clear();
            dashRoot.getChildren().add(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
