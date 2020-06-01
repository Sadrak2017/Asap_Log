package controller;

import java.text.ParseException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    	model.addAttribute("clientes", new Clientes());
    	selectAll();
       return "cliente";
    }
   
	@RequestMapping(value = "/novo", method = RequestMethod.GET)
	public ModelAndView novo(Clientes cliente) {
		ModelAndView mv = new ModelAndView("cliente");
		return mv;
	}
	
    @RequestMapping(value = {"/salvar"},  method = RequestMethod.POST)
    public ModelAndView insert(@Valid @ModelAttribute("clientes") Clientes cliente,
            BindingResult result, RedirectAttributes attributes ) {
    	if(result.hasErrors())
     	  return novo(cliente);
    	else if ((clientesRepository.findCFP(cliente.getCpf()).size()) != 0) {
    	  result.addError(new FieldError("clientes", "cpf", "CPF já está cadastrado!"));
    	  return novo(cliente);
    	}else
    		clientesRepository.save(cliente);	
    	ModelAndView mv = new ModelAndView("redirect:/clientes/novo");
    	attributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso.");
        return mv;
    }
    
    @RequestMapping(value= "/excluir",  method = RequestMethod.POST)
    public ModelAndView delete(@Valid @ModelAttribute("clientes") Clientes cliente,
            BindingResult result, RedirectAttributes attributes) {
    	ModelAndView mv = new ModelAndView("redirect:/clientes/novo");
    	Clientes _rCliente = clientesRepository.findById(cliente.getId()).get();
        List<Apolice> contratosApolices = apoliceRepository.findContrato(_rCliente.getId());
        if(cliente.getOperacao().equals("EXCLUIR")) {
	    	clientesRepository.delete(_rCliente); 
	    	for (Apolice apolices : contratosApolices) { // Exclui todos contratos reencindidos associados ao cliente que foi excluido
	    	  apoliceRepository.delete(apolices);
			}
	    	attributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso.");
        }else {
        	clientesRepository.save(_rCliente);	
        	attributes.addFlashAttribute("mensagem", "Cadastro de cliente editado com sucesso.");
        }
    	return mv;
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