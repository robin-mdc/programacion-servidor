package com.daw.crudapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.model.Direccion;
import com.daw.crudapi.model.EstadoPedido;
import com.daw.crudapi.model.Pedido;
import com.daw.crudapi.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;

@Service
@RequiredArgsConstructor
public class DatosPruebaService {

    // antes: también DireccionRepository y PedidoRepository
    private final ClienteRepository clienteRepository;

    private final Faker faker = new Faker(Locale.forLanguageTag("es-ES"));

    /**
     * Crea clientes nuevos, cada uno con 1-3 direcciones y 0-5 pedidos
     */
    // antes: devolvía ResumenGeneracion y existía también generarPedidos()
    public String generarClientes(int cantidad) {
        // antes: sin límite
        // motivo: una cantidad enorme bloquea la app
        if (cantidad < 1 || cantidad > 100) {
            return "La cantidad debe estar entre 1 y 100";
        }

        for (int i = 0; i < cantidad; i++) {
            Cliente cliente = crearCliente();

            int numDirecciones = faker.number().numberBetween(1, 4);
            for (int d = 0; d < numDirecciones; d++) {
                cliente.addDireccion(crearDireccion());
            }

            int numPedidos = faker.number().numberBetween(0, 6);
            for (int p = 0; p < numPedidos; p++) {
                cliente.addPedido(crearPedido(cliente.getDirecciones()));
            }

            // antes: un save por cada cliente, direccion y pedido
            // motivo: con cascade = ALL, guardar el cliente guarda también sus direcciones y pedidos
            clienteRepository.save(cliente);
        }
        return "Creados " + cantidad + " clientes";
    }

    private Cliente crearCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre(faker.name().firstName());
        cliente.setApellidos(faker.name().lastName() + " " + faker.name().lastName());
        // Fragmento de UUID para que el email no se repita aunque llames varias veces
        String sufijo = UUID.randomUUID().toString().substring(0, 8);
        cliente.setEmail("cliente." + sufijo + "@" + faker.internet().domainName());
        cliente.setTelefono(faker.phoneNumber().cellPhone());
        // antes: sin edad
        // desde 5 años para poder probar la consulta de menores
        cliente.setEdad(faker.number().numberBetween(5, 91));
        return cliente;
    }

    private Direccion crearDireccion() {
        Direccion direccion = new Direccion();
        direccion.setCalle(faker.address().streetName());
        direccion.setNumero(faker.address().buildingNumber());
        direccion.setCiudad(faker.address().city());
        direccion.setCodigoPostal(faker.address().zipCode());
        return direccion;
    }

    // el pedido se envía a una de las direcciones del cliente, elegida al azar
    private Pedido crearPedido(List<Direccion> direcciones) {
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now()
                .minusDays(faker.number().numberBetween(0, 365))
                .minusMinutes(faker.number().numberBetween(0, 1440)));
        pedido.setEstado(faker.options().option(EstadoPedido.class));
        pedido.setDireccion(direcciones.get(faker.number().numberBetween(0, direcciones.size())));
        return pedido;
    }
}
