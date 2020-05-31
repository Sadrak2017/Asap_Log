package controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import Interface.ApoliceRepository;
import Interface.ClienteRepository;
import message.Response;
import model.Apolice;
import model.Clientes;


@Component 
@RequestMapping("/clientes")
public class ClientController {

	@Autowired
	private ClienteRepository clientesRepository;
	
	@Autowired
	private ApoliceRepository apoliceRepository; 
    
    @RequestMapping("/painel")
    public String WebCliente(Model model){
    	model.addAttribute("clientes",  new Clientes());
    	selectAll();
       return "cliente";
    }
   
    @RequestMapping("/salvar")
    public String insert(@RequestParam String name, @RequestParam String city, @RequestParam String uf, @RequestParam String cpf) {
        Clientes newCliente = new Clientes();
        newCliente.setName(name);
        newCliente.setCity(city);
        newCliente.setUf(uf);
        newCliente.setCpf(cpf);
        clientesRepository.save(newCliente);
        selectAll();
        return "redirect:/clientes/painel";
    }
    
    @RequestMapping("/editar")
    public String update(@RequestParam String id, @RequestParam String name, @RequestParam String city, @RequestParam String uf,
    		@RequestParam String cpf){
        Optional<Clientes> cliente = clientesRepository.findById(id);
        cliente.get().setName(name);
        cliente.get().setCity(city);
        cliente.get().setCpf(cpf);
        cliente.get().setUf(uf);
        clientesRepository.save(cliente.get());
        return "redirect:/clientes/painel";
    }

    @RequestMapping("/excluir/{id}")
    @ResponseBody
    public Response delete(@PathVariable String id) {
    	  Clientes cliente = clientesRepository.findById(id).get();
        List<Apolice> contratosApolices = apoliceRepository.findContrato(cliente.getId());
        Response response = new Response("Done", cliente);
    	  clientesRepository.delete(cliente); 
    	  for (Apolice apolices : contratosApolices) { // Exclui todos contratos reencindidos associados ao cliente que foi excluido
    	     apoliceRepository.delete(apolices);
				}
    	return response;
    }
	
    @RequestMapping("/buscarTodos")
    @ResponseBody
    public Response selectAll() {
        Response response = new Response("Done", clientesRepository.findAll());
        return response;
    }
    
    @RequestMapping("buscar/{id}")
    @ResponseBody
    public Response selectOne(@PathVariable String id) throws ParseException {
 	   Clientes cliente = clientesRepository.findById(id).get();
 	   List<Apolice> contrato = null;
 	   Response response = null;
       if(cliente != null && !cliente.getId().isEmpty())
         contrato = ApoliceController.getActiveApolice(apoliceRepository.findContrato(cliente.getId()));
       if(contrato != null)
     	   response = new Response("Done", cliente, contrato);
       else
     	   response = new Response("Done", cliente);    
       return response;
    }
    
    @RequestMapping("/ilike/{name}")
    @ResponseBody
    public Response selectIlike(@PathVariable String name) {	
      Response response = new Response("Done", clientesRepository.findIlikeName(name));
      return response;
    }  

}