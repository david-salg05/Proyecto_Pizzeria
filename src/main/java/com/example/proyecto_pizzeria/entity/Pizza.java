package com.example.proyecto_pizzeria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pizza {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String imagen;
    private boolean activa;
    private int stock;
}
