package controller;


import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Interface.ApoliceRepository;
import Interface.ClienteRepository;
import message.Response;
import model.Apolice;

@Controller
@RequestMapping("/apolices")
public class ApoliceController {

	@Autowired
	private ApoliceRepository apoliceRepository; 
	
	@Autowired
	private ClienteRepository clienteRepository; 
	
	private static final Random contrato = new Random(System.currentTimeMillis());
	
    @RequestMapping("/painel")
    public String WebApolice(Model model){
    	model.addAttribute("apolice",  new Apolice());
       return "apolice";
    }
   
    @RequestMapping("/salvar")
    public String insert(@RequestParam String idCliente, @RequestParam float vlrApolice,
    		@RequestParam String dtInicio,@RequestParam String dtFim,@RequestParam String placa) {
    	contrato.setSeed(6);
        Apolice newApolice = new Apolice();
        newApolice.setVlrApolice(vlrApolice);
        newApolice.setIdCliente(idCliente);
        newApolice.setNrApolice(String.valueOf(contrato.nextInt()));
        newApolice.setPlaca(placa);
        newApolice.setDtInicio(dtInicio);
        newApolice.setDtFim(dtFim);
        apoliceRepository.save(newApolice);
        selectAll();
        return "redirect:/apolices/painel";
    }
    
   @RequestMapping("/buscarTodos")
   @ResponseBody
   public Response selectAll() {
       Response response = new Response("Done", apoliceRepository.findAll());
       return response;
   }
   
   @RequestMapping("buscar/{id}")
   @ResponseBody
   public Response selectOne(@PathVariable String id) {
	   Apolice contrato =  apoliceRepository.findById(id).get();   
	   Response response = new Response("Done", contrato);
       if(contrato != null && !contrato.getIdCliente().isEmpty()) 
    	  response.setData(clienteRepository.findById(contrato.getIdCliente()));
       return response;
   }
   

   @RequestMapping("/editar")
   public String update( @RequestParam String id, @RequestParam String idCliente, @RequestParam String nrApolice, @RequestParam float vlrApolice,
		   @RequestParam String dtInicio, @RequestParam String dtFim, @RequestParam String placa){
       Optional<Apolice> apolice = apoliceRepository.findById(id); 
       apolice.get().setVlrApolice(vlrApolice);
       apolice.get().setPlaca(placa);
       apolice.get().setIdCliente(idCliente);
       apolice.get().setDtInicio(dtInicio);
       apolice.get().setDtFim(dtFim);
       apolice.get().setNrApolice(nrApolice);
       apoliceRepository.save(apolice.get());
       return "redirect:/apolices/painel";
   }


   @RequestMapping("/excluir")
   public String delete(@RequestParam String operacao, @RequestParam String id, @RequestParam String idCliente,@RequestParam String nrApolice, 
		   @RequestParam float vlrApolice,@RequestParam String dtInicio,@RequestParam String dtFim,@RequestParam String placa) {
       String uri;
   	if(operacao.equals("EXCLUIR")) {
   	  Optional<Apolice> cliente = apoliceRepository.findById(id);
   	  apoliceRepository.delete(cliente.get());
      uri = "redirect:/apolices/painel";
    }else {
      uri = update(id, idCliente, nrApolice, vlrApolice, dtInicio, dtFim, placa);
    }
   	return uri;
   }
}
