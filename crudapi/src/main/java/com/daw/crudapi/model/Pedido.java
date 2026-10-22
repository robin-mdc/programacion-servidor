package com.daw.crudapi.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // antes: @Column(nullable = false) sin valor, el insert fallaba si no se asignaba
    // motivo: @CreationTimestamp pone la fecha actual automáticamente al guardar
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado = EstadoPedido.RECIBIDO;

    // antes: @ManyToOne (eager por defecto)
    // motivo: con lazy la direccion solo se carga de bd cuando se usa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    // antes: "forzamos a que cargue el cliente cuando cargue el pedido" (@ManyToOne eager)
    // motivo: cargar siempre el cliente hace consultas innecesarias, con lazy se carga al usarlo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}
