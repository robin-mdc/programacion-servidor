package com.daw.crudapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daw.crudapi.dto.ClienteDTO;
import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.model.Direccion;
import com.daw.crudapi.repository.ClienteRepository;

import net.datafaker.Faker;

// antes: no existía, la lógica estaba dentro del controlador
// motivo: el controlador solo atiende http, la lógica de negocio va en el service
@Service
public class ClienteService {

    final ClienteRepository repoCli;

    // antes: email generado con Math.random() (solo 100000 valores, se podía repetir)
    // motivo: datafaker ya estaba en el pom y genera emails realistas con muchas más combinaciones
    final Faker faker = new Faker();

    ClienteService(ClienteRepository repoCli) {
        this.repoCli = repoCli;
    }

    // readOnly porque solo consulta; la transacción permite leer las direcciones (lazy) al crear el dto
    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return repoCli.findAll().stream().map(ClienteDTO::desde).toList();
    }

    // antes: cada save iba en su propia transacción
    // motivo: con @Transactional todo se guarda junto o, si algo falla, no se guarda nada
    @Transactional
    public ClienteDTO crearClienteDePrueba() {

        Cliente cliente = Cliente.builder()
            .nombre("Pedro")
            .apellidos("Jimenez")
            .email(faker.internet().emailAddress())
            .telefono("323456789")
            .build();

        // Guardamos el cliente en bd con save
        // Esto hace un insert en bd
        // Devuelve una entity que representa al registro guardado en bd
        Cliente clienteGuardado = repoCli.save(cliente);

        // Si modificamos los datos del cliente se hace un update
        // ya que la entity está mapeada en bd
        clienteGuardado.setApellidos("Gonzalez Saavedra");

        // set direccion
        Direccion direccion = new Direccion(null, "Cruz", "15", "Jerez", "24344", null);
        clienteGuardado.addDireccion(direccion);

        // antes: repoCli.save(clienteGuardado);
        // motivo: dentro de @Transactional hibernate detecta los cambios solo, flush los escribe ya para que la direccion tenga id
        repoCli.flush();

        return ClienteDTO.desde(clienteGuardado);
    }

}
