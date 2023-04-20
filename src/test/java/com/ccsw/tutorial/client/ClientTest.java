package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    public static final String NEW_CLIENT = "New Client";
    public static final Long EXISTS_CLIENT_ID = 1L;

    @Test
    public void findAllShouldReturnAllClient() {
        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> client = clientService.findAll();

        assertNotNull(client);
        assertEquals(1, client.size());
    }

    @Test
    public void saveNotExistsClientIdShouldInsert() {
        ClientDto clientDto = new ClientDto();
        clientDto.setName(NEW_CLIENT);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        try {
            clientService.save(null, clientDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        verify(clientRepository).save(client.capture());

        assertEquals(NEW_CLIENT, client.getValue().getName());
    }

    @Test
    public void deleteExistsClientIdShouldDelete() throws Exception {
        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientService.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }
}