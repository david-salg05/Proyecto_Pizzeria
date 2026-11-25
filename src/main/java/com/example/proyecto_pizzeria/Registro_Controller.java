package com.example.proyecto_pizzeria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Registro_Controller {

    @FXML
    TextField nombre;

    @FXML
    TextField usuario;

    @FXML
    TextField correo;

    @FXML
    TextField contrasenha1;

    @FXML
    TextField contrasenha2;

    public void volver(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 440);
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

        stage.setTitle("Bella Pizza");
        stage.setScene(scene);
        stage.show();
    }

    public void registrarse(ActionEvent actionEvent) throws SQLException, IOException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NOTIFICACIÓN");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        //Inicializacion de variables
        String nom = nombre.getText();
        String usu = usuario.getText();
        String corr = correo.getText();
        String con1 = contrasenha1.getText();
        String con2 = contrasenha2.getText();

        if(UsuarioService.registrarCliente(nom, usu, corr, con1, con2)){
            //Muestro la alerta
            alert.setContentText("Cliente registrado correctamente!");
            alert.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 440);
            Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

            stage.setTitle("Bella Pizza - Cliente");
            stage.setScene(scene);
            stage.show();
        }
    }
}
