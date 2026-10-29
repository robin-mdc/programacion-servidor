package com.daw.crudapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.crudapi.model.Cliente;

// consultas derivadas: spring genera la consulta sql a partir del nombre del método
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // select * from cliente where email = ?
    Optional<Cliente> findByEmail(String email);

    // los 8 primeros clientes según el orden que se pase (ej: por edad ascendente)
    List<Cliente> findTop8By(Sort sort);

    // true si existe algún cliente con esa edad o menos
    boolean existsByEdadLessThanEqual(int edad);

}
