package com.example.proyecto_pizzeria.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    private String nombre;
    private String usuario;
    private String email;
    private String password;
    private String tipo;
    private boolean activo;
    private int total_pedidos;
}
