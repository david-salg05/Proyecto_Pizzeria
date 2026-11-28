package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Usuario;
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

public class Login_Controller {

    @FXML
    TextField usuario;

    @FXML
    TextField contrasenha;

    public static Usuario usuarioActual;

    public void registrarse(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registro.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 440);
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

        stage.setTitle("Bella Pizza");
        stage.setScene(scene);
        stage.show();
    }

    public void entrar(ActionEvent actionEvent) throws SQLException, IOException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        //Inicializacion de variables
        boolean correcto = false;
        String usu = usuario.getText();
        String contr = contrasenha.getText();

        if (usu.isEmpty() || contr.isEmpty()){
            //Muestro el error
            alert.setContentText("El usuario o la contraseña están vacíos, inténtelo de nuevo");
            alert.showAndWait();
            return;
        }



        //Compruebo que los datos introducidos sean correctos
        if (UsuarioDAO.consultarUsuario(usu, contr)){

            String tipo = UsuarioDAO.comprobarTipo(usu, contr);

            if (tipo.equalsIgnoreCase("propietario")){

                usuarioActual = UsuarioDAO.devolverUsuario(usu, contr);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drawerProp.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 440);
                Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

                stage.setTitle("Bella Pizza - Propietario");
                stage.setScene(scene);
                stage.show();
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("drawerCli.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 440);
                Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

                stage.setTitle("Bella Pizza - Cliente");
                stage.setScene(scene);
                stage.show();
            }

        }else{
            //Muestro el error
            alert.setContentText("El usuario y/o la contraseña son incorrectos, inténtelo de nuevo");
            alert.showAndWait();
            return;
        }

    }
}
