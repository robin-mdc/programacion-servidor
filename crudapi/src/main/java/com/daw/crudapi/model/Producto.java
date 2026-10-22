package com.daw.crudapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
 
@Data    // Lombok: genera getters, setters, toString, equals y hashCode
@Entity  // JPA: esta clase se corresponde con una tabla de la base de datos
public class Producto {
 
    @Id                                                  // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Valor autoincremental
    private Long id;
 
    private String nombre;
    private double precio;
}

