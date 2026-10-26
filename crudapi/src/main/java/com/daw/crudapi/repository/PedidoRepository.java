package com.daw.crudapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.daw.crudapi.model.EstadoPedido;
import com.daw.crudapi.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Consulta derivada de ejemplo, la usaremos en los tests del apartado 6
    List<Pedido> findByEstado(EstadoPedido estado);
}