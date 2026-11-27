package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.entity.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TopClientes_Controller {

    @FXML
    TableView<Usuario> tablaClientesTop;

    @FXML
    TableColumn<Usuario, String> clienteCol;

    @FXML
    TableColumn<Usuario, String> pedidosCol;


}
