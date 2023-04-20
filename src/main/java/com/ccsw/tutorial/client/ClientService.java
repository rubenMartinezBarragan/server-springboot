package com.ccsw.tutorial.client;

import java.util.List;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

/**
 * @author ruben martinez barragan
 * 
 */
public interface ClientService {

    /**
     * Recupera un {@link Client} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Client}
     */
    Client get(Long id);

    /**
     * Método para recuperar todos los clientes
     *
     * @return {@link List} de {@link Client}
     */
    List<Client> findAll();

    /**
     * Método para crear o actualizar un cliente
     *
     * @param id  PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ClientDto dto) throws Exception;

    /**
     * Método para borrar una {@link Client}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

}