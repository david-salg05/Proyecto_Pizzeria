module com.example.proyecto_pizzeria {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyecto_pizzeria to javafx.fxml;
    exports com.example.proyecto_pizzeria;
}