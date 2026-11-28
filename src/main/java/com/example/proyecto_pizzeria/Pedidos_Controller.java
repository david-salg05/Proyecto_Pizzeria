
package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Pedido;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class Pedidos_Controller {

    @FXML
    private TableView<Pedido> tablaPedidos;

    @FXML
    private TableColumn<Pedido, String> cliente;

    @FXML
    private TableColumn<Pedido, String> fecha;

    @FXML
    private TableColumn<Pedido, String> pizzas;

    @FXML
    private TableColumn<Pedido, String> estado;

    @FXML
    private TableColumn<Pedido, Void> acciones;

    @FXML
    public void initialize() {

        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        pizzas.setCellValueFactory(new PropertyValueFactory<>("pizzas"));
        estado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        try {
            tablaPedidos.setItems(FXCollections.observableArrayList(
                    UsuarioDAO.devolverPedidos()
            ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creo el ComboBox para cada fila de la columna Estado
        estado.setCellFactory(param -> new TableCell<>() {

            private final ComboBox<String> comboEstado = new ComboBox<>(
                    FXCollections.observableArrayList(
                            "pendiente",
                            "preparando",
                            "entregado"
                    )
            );

            private boolean actualizando = false;

            {
                comboEstado.setMaxWidth(Double.MAX_VALUE);

                comboEstado.setOnAction(event -> {

                    if (!actualizando && getTableRow() != null) {

                        Pedido pedido = getTableRow().getItem();

                        if (pedido != null) {

                            String estadoAnterior = pedido.getEstado();
                            String nuevoEstado = comboEstado.getValue();

                            try {
                                boolean modificado = UsuarioDAO.modificarEstadoPedido(
                                        pedido.getId(), nuevoEstado
                                );

                                if (modificado) {
                                    pedido.setEstado(nuevoEstado);
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();

                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setContentText("No se pudo modificar el estado del pedido.");
                                alert.showAndWait();

                                actualizando = true;
                                comboEstado.setValue(estadoAnterior);
                                actualizando = false;
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    actualizando = true;
                    comboEstado.setValue(item);
                    actualizando = false;

                    setGraphic(comboEstado);
                }
            }
        });
    }
}
