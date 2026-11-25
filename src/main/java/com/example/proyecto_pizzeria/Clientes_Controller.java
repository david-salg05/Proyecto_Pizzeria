
package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.entity.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.sql.SQLException;
import java.util.ArrayList;

public class Clientes_Controller {

    @FXML
    TableView<Usuario> tablaClientes;

    @FXML
    TableColumn<Usuario, String> colNombre;

    @FXML
    TableColumn<Usuario, String> colUsuario;

    @FXML
    TableColumn<Usuario, String> colCorreo;

    @FXML
    TableColumn<Usuario, Void> colAcciones;

    @FXML
    TextField barraBuscar;

    @FXML
    public void initialize() throws SQLException {

        // Indico qué atributo se muestra en cada columna
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        colCorreo.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Obtengo todos los clientes de la BD
        ArrayList<Usuario> listaClientes = UsuarioService.devolverClientes();

        // Los añado a la tabla
        tablaClientes.getItems().addAll(listaClientes);

        // Creo un boton para cada fila
        colAcciones.setCellFactory(param -> new TableCell<>() {

            private final Button botonEliminar = new Button("Eliminar usuario");

            {
                botonEliminar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    eliminarCliente(usuario);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonEliminar);
                }
            }
        });
    }

    // Funcion que elimina un cliente
    public void eliminarCliente(Usuario usuario) {

        try {
            boolean eliminado = UsuarioService.eliminarCliente(usuario.getUsuario());

            if (eliminado) {
                tablaClientes.getItems().remove(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscar(ActionEvent actionEvent) throws SQLException {

        // Creo la alerta
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ERROR");

        // Aplico un tamaño a la alerta
        alert.getDialogPane().setPrefWidth(450);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(450);

        String texto = barraBuscar.getText();

        ArrayList<Usuario> listaClientes = UsuarioService.buscarClientes(texto);

        tablaClientes.getItems().clear();
        tablaClientes.getItems().addAll(listaClientes);

        if (listaClientes.isEmpty()) {
            alert.setTitle("Información");
            alert.setContentText("No se encontraron clientes");
            alert.showAndWait();
        }
    }
}
