package PruebaPractica.Bedoya.Domain.Service;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    public ClientRepository repository;

    public Client save(Client client){
        return repository.save(client);
    }

    public Optional<Client> find(long id){
        return repository.find(id);
    }
}
