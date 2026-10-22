package com.daw.crudapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String calle;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String codigoPostal;

    // antes: "una direccion tiene muchos clientes"
    // motivo: el comentario estaba al revés, ManyToOne = muchas direcciones pertenecen a un cliente
    // antes: @ManyToOne (eager por defecto)
    // motivo: con lazy el cliente solo se carga de bd cuando se usa
    @ManyToOne(fetch = FetchType.LAZY)
    // usamos joincolumn para especificar el nombre de la fk, apunta automaticamente al
    // id de la tabla cliente porque usamos la clase cliente
    // ponemos nullable false ya que no puede havber direcciones sin clientes asignado
    @JoinColumn(name= "cliente_id", nullable = false)
    private Cliente cliente;

}
