package com.daw.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.crudapi.model.Producto;

// antes: ProductRepository
// motivo: mismo idioma que la entidad (Producto) y que ClienteRepository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // No hace falta código adicional: JpaRepository ya ofrece findAll(),
    // findById(), save(), deleteById(), existsById(), etc.
}
