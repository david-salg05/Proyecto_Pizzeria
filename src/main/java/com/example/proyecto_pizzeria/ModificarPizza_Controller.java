package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Pizza;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModificarPizza_Controller {

    @FXML
    private TextField nombre;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField precio;

    @FXML
    private TextField imagen;

    private int idPizza;

    // Recibo el ID de la pizza seleccionada
    public void setIdPizza(int id) {

        this.idPizza = id;

        try {
            // Busco la pizza en la base de datos
            Pizza pizza = UsuarioDAO.devolverPizza(idPizza);

            if (pizza != null) {
                // Cargo sus datos en los TextField
                nombre.setText(pizza.getNombre());
                descripcion.setText(pizza.getDescripcion());
                precio.setText(String.valueOf(pizza.getPrecio()));
                imagen.setText(pizza.getImagen());
            }

        } catch (SQLException e) {
            e.printStackTrace();
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


    public void guardar(ActionEvent actionEvent) {
        String nom = nombre.getText();
        String descrip = descripcion.getText();
        String pr = precio.getText();
        String img = imagen.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Compruebo que los campos estén completos
        if (nom.isEmpty() || descrip.isEmpty() || pr.isEmpty() || img.isEmpty()) {
            alert.setTitle("ERROR");
            alert.setContentText("Todos los campos deben estar completos.");
            alert.showAndWait();
            return;
        }

        try {
            // Modifico los datos de la pizza
            boolean modificado = UsuarioDAO.modificarPizza(idPizza, nom, descrip, img, pr);

            if (modificado) {
                alert.setTitle("Correcto");
                alert.setContentText("Pizza modificada correctamente.");
                alert.showAndWait();

                volver(actionEvent);
            }

        } catch (NumberFormatException e) {
            alert.setTitle("ERROR");
            alert.setContentText("El precio debe ser un número válido.");
            alert.showAndWait();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

