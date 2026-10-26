package com.daw.crudapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.crudapi.model.Cliente;
import com.daw.crudapi.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

// antes: no existía, la lógica estaba dentro del controlador
// motivo: el controlador solo atiende http, la lógica de negocio va en el service
@Service
// antes: constructor a mano y @Autowired en campos
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repoCli;

    public List<Cliente> listar() {
        return repoCli.findAll();
    }

}
