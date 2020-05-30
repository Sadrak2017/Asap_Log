package service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Interface.ClienteRepository;
import model.Clientes;


@Service
public class ClientService{

	@Autowired
    private ClienteRepository clientesRepository;
 
    public void salvar(String name, String city, String uf, String cpf) {
    	clientesRepository.save(new Clientes(name, city, uf, cpf));
    }
 
    public List<Clientes> findAll() {
       return (List<Clientes>) clientesRepository.findAll();
    }
 
    public long count() {
        return clientesRepository.count();
    }
 
    public Optional<Clientes> findById(String id) {
        return clientesRepository.findById(id);
    }
 
    public void delete(String id) {
    	clientesRepository.deleteById(id);
    }
	 
	
}
