package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class Inicio_Controller {
    @FXML
    private Label numeroPizzas;

    @FXML
    private Label numeroClientes;

    @FXML
    private Label numeroPedidos;

    @FXML
    private Label dinero;

    @FXML
    public void initialize() {

        try {

            numeroPizzas.setText(String.valueOf(UsuarioDAO.contarPizzasDisponibles()));

            numeroClientes.setText(String.valueOf(UsuarioDAO.contarClientes()));

            numeroPedidos.setText(String.valueOf(UsuarioDAO.contarPedidos()));

            dinero.setText(NumberFormat.getCurrencyInstance(new Locale("es", "ES")).format(UsuarioDAO.dineroDisponible()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
