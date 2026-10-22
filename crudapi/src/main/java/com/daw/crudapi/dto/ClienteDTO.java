package com.daw.crudapi.dto;

import java.util.List;

import com.daw.crudapi.model.Cliente;

// antes: no existía, se habría devuelto la entity Cliente directamente
// motivo: con un dto elegimos qué datos salen en el json y evitamos la recursión de las relaciones
public record ClienteDTO(
    Long id,
    String nombre,
    String apellidos,
    String email,
    String telefono,
    List<DireccionDTO> direcciones) {

    public static ClienteDTO desde(Cliente cliente) {
        return new ClienteDTO(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getApellidos(),
            cliente.getEmail(),
            cliente.getTelefono(),
            cliente.getDirecciones().stream().map(DireccionDTO::desde).toList());
    }

}
