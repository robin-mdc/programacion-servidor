package com.daw.crudapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.model.Direccion;
import com.daw.crudapi.repository.ClienteRepository;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/crudapi")
public class MyRestController {

    final ClienteRepository repoCli;

    MyRestController(ClienteRepository repoCli) {
        this.repoCli = repoCli;
    }

    @GetMapping
    public String index() {

        Cliente cliente = Cliente.builder()
            .nombre("Pedro")
            .apellidos("Jimenez")
            .email(randomEmail())
            .telefono("323456789")
            .build();

        // Guardamos el cliente en bd con save
        // Esto hace un insert en bd
        // Devuelve una entity que representa al registro guardado en bd
        Cliente clienteGuardado = repoCli.save(cliente);

        // Si modificamos los datos del cliente y hacemos save otra vez
        // En este caso hace un update ya que la entity está mapeada en bd 
        clienteGuardado.setApellidos("Gonzalez Saavedra");

        // set direccion
        Direccion direccion = new Direccion(null, "Cruz", "15", "Jerez", "24344", null);
        clienteGuardado.addDireccion(direccion);

        repoCli.save(clienteGuardado);

        return new String("Página Principal"); 

    }

    public String randomEmail() {
        return "correo" + (int)(Math.random() * 100000) + "@mail.com";
    }

}
