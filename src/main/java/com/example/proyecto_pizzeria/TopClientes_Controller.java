package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class TopClientes_Controller {

    @FXML
    TableView<Usuario> tablaClientesTop;

    @FXML
    TableColumn<Usuario, String> clienteCol;

    @FXML
    TableColumn<Usuario, String> pedidosCol;

    @FXML
    public void initialize() throws SQLException {

        // Indico qué atributo se muestra en cada columna
        clienteCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        pedidosCol.setCellValueFactory(new PropertyValueFactory<>("total_pedidos"));

        // Obtengo todos los clientes de la BD
        ArrayList<Usuario> listaClientes = UsuarioDAO.devolverClientesTop();

        // Los añado a la tabla
        tablaClientesTop.getItems().addAll(listaClientes);
    }
}
