package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author ruben martinez barragan
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * Metodo implementado por rmb para recuperar este objeto de BBDD y asignarlo en
     * el objeto Loan
     * 
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {
        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ClientDto dto) throws Exception {
        Client client;

        if (id == null)
            client = new Client();
        else
            client = this.clientRepository.findById(id).orElse(null);

        boolean existeCliente = findAll().stream().anyMatch(element -> element.getName().equals(dto.getName()));

        if (!existeCliente) {
            client.setName(dto.getName());
            this.clientRepository.save(client);
        } else
            throw new Exception("NO se puede dar de alta a un cliente con el mismo nombre que otro existente");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {
        if (this.clientRepository.findById(id).orElse(null) == null)
            throw new Exception("El cliente que se quiere dar de baja no existe");

        this.clientRepository.deleteById(id);
    }

}