package PruebaPractica.Bedoya.web.controller;

import PruebaPractica.Bedoya.Domain.DTO.Client;
import PruebaPractica.Bedoya.Domain.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @PostMapping("/new")
    public ResponseEntity<Client> create(@RequestBody Client client){
        return new ResponseEntity<>(service.save(client), HttpStatus.OK);
    }

}
