package com.example.proyecto_pizzeria;

import com.example.proyecto_pizzeria.DAO.UsuarioDAO;
import com.example.proyecto_pizzeria.entity.Pizza;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Pizzas_Controller {

    @FXML
    private TableView<Pizza> tablaPizzas;

    @FXML
    private TableColumn<Pizza, String> columnaNombre;

    @FXML
    private TableColumn<Pizza, Double> columnaPrecio;

    @FXML
    private TableColumn<Pizza, Integer> columnaStock;

    @FXML
    private TableColumn<Pizza, Void> columnaAcciones;

    @FXML
    public void initialize() {

        // Indicamos qué atributo de Pizza corresponde a cada columna
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnaStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        try {
            // Cargamos las pizzas en la tabla
            tablaPizzas.setItems(FXCollections.observableArrayList(
                    UsuarioDAO.devolverPizzas()
            ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creamos los botones para cada fila
        columnaAcciones.setCellFactory(param -> new TableCell<>() {

            private final Button botonEditar = new Button("Editar");
            private final Button botonEliminar = new Button("Eliminar");

            {
                // Acción del botón Editar
                botonEditar.setOnAction(event -> {

                    Pizza pizza = getTableView().getItems().get(getIndex());

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("modificarPizza.fxml")
                        );

                        Scene scene = new Scene(fxmlLoader.load(), 600, 440);

                        // Obtengo el controller de la ventana de modificación
                        ModificarPizza_Controller controller = fxmlLoader.getController();

                        // Paso el ID de la pizza seleccionada
                        controller.setIdPizza(pizza.getId());

                        Stage stage = (Stage) ((Node) event.getSource())
                                .getScene().getWindow();

                        stage.setTitle("Bella Pizza - Propietario");
                        stage.setScene(scene);
                        stage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Acción del botón Eliminar
                botonEliminar.setOnAction(event -> {

                    Pizza pizza = getTableView().getItems().get(getIndex());

                    eliminarPizza(pizza);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox botones = new HBox(5);
                    botones.getChildren().addAll(botonEditar, botonEliminar);

                    setGraphic(botones);
                }
            }
        });
    }

    // Funcion que crea una nueva pizza
    public void nuevaPizza(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("nuevaPizza.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 600, 440);

        Stage stage = (Stage) ((Node) (actionEvent.getSource()))
                .getScene().getWindow();

        stage.setTitle("Nueva Pizza");
        stage.setScene(scene);
        stage.show();
    }

    // Funcion que da de baja una pizza
    public void eliminarPizza(Pizza pizza) {

        try {
            boolean eliminado = UsuarioDAO.darDeBajaPizza(pizza.getId());

            if (eliminado) {
                tablaPizzas.getItems().remove(pizza);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}