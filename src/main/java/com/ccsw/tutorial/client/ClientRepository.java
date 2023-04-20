package com.ccsw.tutorial.client;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.client.model.Client;

/**
 * @author ruben martinez barragan
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

}