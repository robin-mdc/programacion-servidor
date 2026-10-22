package com.daw.crudapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.daw.crudapi.model.Producto;
 
public interface ProductRepository extends JpaRepository<Producto, Long> {
    // No hace falta código adicional: JpaRepository ya ofrece findAll(),
    // findById(), save(), deleteById(), existsById(), etc.
}

