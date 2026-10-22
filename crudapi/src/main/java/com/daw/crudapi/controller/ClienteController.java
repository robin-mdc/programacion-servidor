package com.daw.crudapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daw.crudapi.dto.ClienteDTO;
import com.daw.crudapi.service.ClienteService;

// antes: MyRestController con @RequestMapping("/crudapi")
// motivo: el nombre indica el recurso que gestiona y la url sigue el estilo rest (/clientes)
@RestController
@RequestMapping("/crudapi/clientes")
public class ClienteController {

    // antes: usaba ClienteRepository directamente
    // motivo: el controlador delega en el service y no accede a la bd
    final ClienteService clienteService;

    ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // antes: el GET creaba un cliente en bd cada vez que se recargaba la página
    // motivo: GET solo debe leer, ahora devuelve la lista de clientes
    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listar();
    }

    // antes: devolvía new String("Página Principal")
    // motivo: crear datos se hace con POST y se devuelve lo creado con código 201 (created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crear() {
        return clienteService.crearClienteDePrueba();
    }

}
