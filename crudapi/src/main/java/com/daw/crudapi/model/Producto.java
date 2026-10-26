package com.daw.crudapi.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// antes: @Data (getters, setters, toString, equals y hashCode)
// motivo: equals/hashCode con todos los campos dan problemas en entidades jpa
@Getter
@Setter
@NoArgsConstructor
@Entity  // JPA: esta clase se corresponde con una tabla de la base de datos
public class Producto {

    @Id                                                  // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Valor autoincremental
    private Long id;

    // antes: sin @Column
    @Column(nullable = false)
    private String nombre;

    // antes: private double precio;
    // motivo: double tiene errores de redondeo, para dinero se usa BigDecimal
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}
