package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
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

public class NuevaPizza_Controller {

    @FXML
    TextField nombre;

    @FXML
    TextField descripcion;

    @FXML
    TextField precio;

    @FXML
    TextField imagen;

    //Guardo los datos en variables y se los paso al DAO para crear una nueva pizza
    public void anhadir(ActionEvent actionEvent) throws SQLException, IOException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        String nom = "", descrip = "", img = "", pr = "";

        nom = nombre.getText();
        descrip = descripcion.getText();
        img = imagen.getText();
        pr = precio.getText();

        if (nom.isEmpty() || descrip.isEmpty() || img.isEmpty() || pr.isEmpty()){
            //Muestro el error
            alert.setContentText("Todos los campos deben estar completos.");
            alert.showAndWait();
            return;
        }

        if(UsuarioDAO.crearPizza(nom,descrip,img,pr)){
            //Muestro el mensaje
            alert.setContentText("Pizza creada correctamente!");
            alert.showAndWait();

        }else{
            //Muestro el mensaje
            alert.setContentText("La pizza no se ha podido registrar, vualva a intentarlo.");
            alert.showAndWait();
        }
    }

    public void volver(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drawerProp.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 440);
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

        stage.setTitle("Bella Pizza - Propietario");
        stage.setScene(scene);
        stage.show();
    }
}
