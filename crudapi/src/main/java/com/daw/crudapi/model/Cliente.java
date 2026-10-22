package com.daw.crudapi.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// antes: @Table(indexes = @Index(name = "idx_email", columnList = "email"))
// motivo: unique = true en email ya crea un índice, era redundante
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    // en mappedby va el nombre del campo de la otra entidad
    // que sirve para mapear esta
    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones = new ArrayList<>();

    // añadimos la direccion a la lista de direcciones del cliente
    public void addDireccion(Direccion direccion) {
        direcciones.add(direccion);
        // le decimos que la direccion pertenece a este cliente
        direccion.setCliente(this);
    }

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    // Funcion para añadir correctamente un pedido a este cliente
    // añadimos el pedido a la lista de pedidos del cliente
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        // le decimos que el pedido pertenece a este cliente
        pedido.setCliente(this);
    }

    // antes: comentario suelto "hello class today we are"
    // motivo: no aportaba nada al código

}
