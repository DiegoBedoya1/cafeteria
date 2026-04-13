package PruebaPractica.Bedoya.Domain.Repository;

import PruebaPractica.Bedoya.Domain.DTO.Client;

import java.util.Optional;

public interface ClientRepository {

    Client save(Client client);
    Optional<Client> find(long id);
}
