package com.daw.crudapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.crudapi.service.DatosPruebaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/crudapi/datos")
@RequiredArgsConstructor
public class DatosPruebaController {

    private final DatosPruebaService datosPruebaService;

    // antes: GET /datos/clientes y GET /datos/pedidos
    // motivo: crear datos se hace con POST, con GET cada recarga del navegador insertaba datos
    // POST http://localhost:8080/crudapi/datos?cantidad=10
    @PostMapping
    public String generarClientes(@RequestParam(defaultValue = "5") int cantidad) {
        return datosPruebaService.generarClientes(cantidad);
    }
}
