package PruebaPractica.Bedoya;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.Repository.ClientRepository;
import PruebaPractica.Bedoya.Persistance.Crud.ClienteCrudRepository;
import PruebaPractica.Bedoya.Persistance.Entity.Cliente;
import PruebaPractica.Bedoya.Persistance.Mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteRepository implements ClientRepository {

    @Autowired
    private ClienteCrudRepository crud;

    @Autowired
    private ClientMapper mapper;
    @Override
    public Client save(Client client) {
        Cliente cliente = mapper.toCliente(client);
        cliente.setEstado(false);
        return mapper.toClient(crud.save(cliente));
    }

    @Override
    public Optional<Client> find(long id){
       return crud.findById(id)
               .map(mapper::toClient);
    }

}
