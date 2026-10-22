package com.daw.crudapi.dto;

import com.daw.crudapi.model.Direccion;

// antes: no existía, se habría devuelto la entity Direccion directamente
// motivo: la entity tiene el campo cliente, y Cliente -> direcciones -> cliente... da recursión infinita en el json
public record DireccionDTO(Long id, String calle, String numero, String ciudad, String codigoPostal) {

    public static DireccionDTO desde(Direccion direccion) {
        return new DireccionDTO(
            direccion.getId(),
            direccion.getCalle(),
            direccion.getNumero(),
            direccion.getCiudad(),
            direccion.getCodigoPostal());
    }

}
