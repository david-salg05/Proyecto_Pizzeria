package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioService {

    //Funcion que comprueba que los datos de login no esten vacios y que se encuentren en la bd
    public static boolean consultarUsuario(String usu, String contr) throws SQLException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        if (usu.isEmpty() || contr.isEmpty()){
            //Muestro el error
            alert.setContentText("El usuario o la contraseña están vacíos, inténtelo de nuevo");
            alert.showAndWait();
            return false;
        }

        return UsuarioDAO.consultarUsuario(usu, contr);
    }

    //Funcion que comprueba si el usuario logueado es cliente o propietario
    public static String comprobarTipo(String usu, String contr) throws SQLException {
        return UsuarioDAO.comprobarTipo(usu, contr);
    }

    //Funcion que comprueba que los campos de registro estan cubiertos y guarda el cliente
    public static boolean registrarCliente(String nom, String usu, String corr, String con1, String con2) throws SQLException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        if(nom.isEmpty() || usu.isEmpty() || corr.isEmpty() || con1.isEmpty() || con2.isEmpty()){
            //Muestro el error
            alert.setContentText("Debes rellenar todos los campos");
            alert.showAndWait();
            return false;
        }

        else if(!con1.equalsIgnoreCase(con2)){
            //Muestro el error
            alert.setContentText("Las contraseñas no son iguales, escríbelas de nuevo");
            alert.showAndWait();
            return false;
        }
        return UsuarioDAO.registrarCliente(nom, usu, corr, con1);
    }

    //Funcion que devuelve una lista con los clientes registrados en la bd
    public static ArrayList<Usuario> devolverClientes() throws SQLException {

        //Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        //Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        ArrayList<Usuario> lista = UsuarioDAO.devolverClientes();

        if (lista.isEmpty()) {
            //Muestro el error
            alert.setContentText("No hay clientes dados de alta en la bd");
            alert.showAndWait();
            return null;
        }

        return lista;
    }

    //Funcion que busca un cliente por un texto que le envio por parametro
    public static ArrayList<Usuario> buscarClientes(String texto) throws SQLException {
        return UsuarioDAO.buscarClientes(texto);
    }

    //Funcion que elimina un cliente seleccionado
    public static boolean eliminarCliente(String usu) throws SQLException {
        return UsuarioDAO.eliminarCliente(usu);
    }
}
