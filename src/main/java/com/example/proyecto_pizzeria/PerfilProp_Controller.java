package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class PerfilProp_Controller {

    @FXML
    private TextField nombre;

    @FXML
    private TextField usuario;

    @FXML
    private TextField correo;

    @FXML
    private TextField contrasenha;

    @FXML
    public void initialize() {

        Usuario us = Login_Controller.usuarioActual;

        if (us != null) {
            nombre.setText(us.getNombre());
            usuario.setText(us.getUsuario());
            correo.setText(us.getEmail());
            contrasenha.setText(us.getPassword());
        }
    }

    public void guardar(ActionEvent actionEvent) {

        String nom = nombre.getText();
        String usu = usuario.getText();
        String corr = correo.getText();
        String contr = contrasenha.getText();

        if (nom.isEmpty() || usu.isEmpty() || corr.isEmpty() || contr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setContentText("Debes rellenar todos los campos.");
            alert.showAndWait();
            return;
        }

        try {

            Usuario us = Login_Controller.usuarioActual;

            boolean modificado = UsuarioDAO.modificarDatosPropietario(
                    us.getUsuario(), nom, usu, corr, contr
            );

            if (modificado) {

                // Actualizo los datos del usuario que ha iniciado sesion
                us.setNombre(nom);
                us.setUsuario(usu);
                us.setEmail(corr);
                us.setPassword(contr);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Perfil");
                alert.setContentText("Tus datos se han actualizado correctamente.");
                alert.showAndWait();

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No se pudieron modificar los datos.");
                alert.showAndWait();

            }

        } catch (SQLException e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ha ocurrido un error al guardar los cambios.");
            alert.showAndWait();
        }
    }
}
