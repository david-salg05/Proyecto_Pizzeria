package com.example.proyecto_pizzeria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    private int id;
    private String cliente;
    private String fecha;
    private String pizzas;
    private String estado;
}
