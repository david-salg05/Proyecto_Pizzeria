package com.example.proyecto_pizzeria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawerProp_Controller {

    @FXML
    Button inicio, pizzas, clientes, topClientes, pedidos;

    @FXML
    private StackPane contenedorContenido;

    @FXML
    public void initialize() {
        cargarVista("/com/example/proyecto_pizzeria/propietario/inicio.fxml");
    }

    //Funcion que carga la vista de la ruta que le pasemos como parametro
    private void cargarVista(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent vista = loader.load();

            contenedorContenido.getChildren().clear();

            contenedorContenido.getChildren().add(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Funcion que muestra la ventana de inicio
    public void mostrarInicio(ActionEvent actionEvent) {
        cargarVista("/com/example/proyecto_pizzeria/propietario/inicio.fxml");
    }

    //Funcion que muestra las pizzas disponibles
    public void mostrarPizzas(ActionEvent actionEvent) {
        cargarVista("/com/example/proyecto_pizzeria/propietario/pizzas.fxml");
    }

    //Funcion que muestra los clientes registrados
    public void mostrarClientes(ActionEvent actionEvent) {
        cargarVista("/com/example/proyecto_pizzeria/propietario/clientes.fxml");
    }

    //Funcion que muestra los pedidos realizados
    public void mostrarPedidos(ActionEvent actionEvent) {
        cargarVista("/com/example/proyecto_pizzeria/propietario/pedidos.fxml");
    }

    //Funcion que muestra los clientes que mas piden
    public void mostrarTopClientes(ActionEvent actionEvent) {
        cargarVista("/com/example/proyecto_pizzeria/propietario/topClientes.fxml");
    }

    //Funcion que muestra el perfil del usuario que ha iniciado sesion
    public void mostrarPerfil(ActionEvent actionEvent) throws IOException {
        cargarVista("/com/example/proyecto_pizzeria/perfilProp.fxml");
    }

    //FUncion que vuelve a la ventana de login
    public void cerrarSesion(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 440);
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();

        stage.setTitle("Bella Pizza");
        stage.setScene(scene);
        stage.show();
    }
}
