package com.daw.crudapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    // GET http://localhost:8080/crudapi/clientes
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    // GET http://localhost:8080/crudapi/clientes/pagina?numero=0&tamano=10
    @GetMapping("/pagina")
    public List<Cliente> listarPagina(@RequestParam(defaultValue = "0") int numero,
                                      @RequestParam(defaultValue = "10") int tamano) {
        return clienteService.listarPagina(numero, tamano);
    }

    // GET http://localhost:8080/crudapi/clientes/1
    // ResponseEntity.of devuelve 200 con el cliente, o 404 si el Optional está vacío
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.of(clienteService.buscarPorId(id));
    }

    // GET http://localhost:8080/crudapi/clientes/buscar?email=cliente.1234abcd@ejemplo.com
    @GetMapping("/buscar")
    public ResponseEntity<Cliente> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.of(clienteService.buscarPorEmail(email));
    }

    // GET http://localhost:8080/crudapi/clientes/jovenes
    @GetMapping("/jovenes")
    public List<Cliente> masJovenes() {
        return clienteService.masJovenes();
    }

    // GET http://localhost:8080/crudapi/clientes/menores?edad=18
    @GetMapping("/menores")
    public String existenMenores(@RequestParam(defaultValue = "18") int edad) {
        return clienteService.existenConEdadMaxima(edad)
                ? "Existen clientes con " + edad + " años o menos"
                : "No existen clientes con " + edad + " años o menos";
    }

    // DELETE http://localhost:8080/crudapi/clientes/1
    // 204 (sin contenido) si se borra, 404 si no existe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        return clienteService.borrar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
