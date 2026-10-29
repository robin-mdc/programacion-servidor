package com.daw.crudapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.repository.ClienteRepository;
import com.daw.crudapi.repository.PedidoRepository;

import lombok.RequiredArgsConstructor;

// antes: no existía, la lógica estaba dentro del controlador
// motivo: el controlador solo atiende http, la lógica de negocio va en el service
@Service
// antes: constructor a mano y @Autowired en campos
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repoCli;
    private final PedidoRepository repoPed;

    public List<Cliente> listar() {
        return repoCli.findAll();
    }

    // una página de clientes ordenados por nombre (la primera página es la 0)
    public List<Cliente> listarPagina(int pagina, int tamano) {
        Pageable paginacion = PageRequest.of(pagina, tamano, Sort.by("nombre").ascending());
        // el Page tiene los clientes de la página y datos como el total de páginas
        Page<Cliente> resultado = repoCli.findAll(paginacion);
        // con getContent sacamos solo la lista de clientes
        return resultado.getContent();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repoCli.findById(id);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return repoCli.findByEmail(email);
    }

    public List<Cliente> masJovenes() {
        return repoCli.findTop8By(Sort.by("edad").ascending());
    }

    public boolean existenConEdadMaxima(int edad) {
        return repoCli.existsByEdadLessThanEqual(edad);
    }

    // devuelve false si no existía el cliente
    // @Transactional: los dos borrados se hacen juntos o ninguno
    @Transactional
    public boolean borrar(Long id) {
        Optional<Cliente> cliente = repoCli.findById(id);
        if (cliente.isEmpty()) {
            return false;
        }
        // primero los pedidos: apuntan a las direcciones, y si una direccion
        // se borra antes que su pedido, falla la clave foránea
        repoPed.deleteAll(cliente.get().getPedidos());
        // con cascade = ALL se borran también sus direcciones
        repoCli.delete(cliente.get());
        return true;
    }

}
