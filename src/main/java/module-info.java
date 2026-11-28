module com.example.proyecto_pizzeria {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;


    opens com.example.proyecto_pizzeria to javafx.fxml;
//  exports com.example.proyecto_pizzeria.main;
   // opens com.example.proyecto_pizzeria.main to javafx.fxml;
    exports com.example.proyecto_pizzeria.main.clientes;
    opens com.example.proyecto_pizzeria.main.clientes to javafx.fxml;
    exports com.example.proyecto_pizzeria;
    opens com.example.proyecto_pizzeria.entity to javafx.base;
}