package com.daw.crudapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.service.ClienteService;

import lombok.RequiredArgsConstructor;

// antes: MyRestController con @RequestMapping("/crudapi")
@RestController
@RequestMapping("/crudapi/clientes")
// antes: constructor escrito a mano
@RequiredArgsConstructor
public class ClienteController {

    // antes: usaba ClienteRepository directamente
    private final ClienteService clienteService;

    // antes: el GET creaba un cliente en bd cada vez que se recargaba la página
    // motivo: GET solo debe leer; los datos de prueba se crean con POST /crudapi/datos
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

}
