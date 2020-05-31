package controller;

import java.text.ParseException;
import java.util.List;
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
import model.Clientes;

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
    public String insert(@RequestParam String idCliente, @RequestParam String inativo, @RequestParam float vlrApolice,
    		@RequestParam String dtInicio,@RequestParam String dtFim,@RequestParam String placa) {
    	   contrato.setSeed(6);
        Apolice newApolice = new Apolice();
        newApolice.setVlrApolice(vlrApolice);
        newApolice.setIdCliente(idCliente);
        newApolice.setInativo(inativo.equals("0") ? true : false);
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
   
	 @RequestMapping("/consulta/{idCliente}")
   @ResponseBody
   public Response selectWhere(@PathVariable String idCliente){
	   Clientes cliente = null;
	   List<Apolice> contratos =  apoliceRepository.findContrato(idCliente);
	   for (Apolice apolice : contratos) {
			apolice.setStatus(getStatus(apolice.getInativo(), apolice.getDtFim()));;
		 }
	   Response responses = new Response("Done", contratos);
     return responses;
   }
	 
	 @RequestMapping("/consultaContrato/{id}")
   @ResponseBody
   public Response selectContrato(@PathVariable String id){
	   Apolice contratos =  apoliceRepository.findById(id).get();
	   System.out.println(id);
	   contratos.setStatus(getStatus(contratos.getInativo(), contratos.getDtFim()));;
	   Response responses = new Response("Done", contratos);;
     return responses;
   }
   
   @RequestMapping("buscar/{id}")
   @ResponseBody
   public Response selectOne(@PathVariable String id) throws ParseException {
	   Clientes cliente = null;
	   Response response = null;
	   Apolice contrato =  apoliceRepository.findById(id).get();   
	   contrato.setStatus(getStatus(contrato.getInativo(), contrato.getDtFim()));
       if(contrato != null && !contrato.getIdCliente().isEmpty()) 
    	   cliente = clienteRepository.findById(contrato.getIdCliente()).get();
       if(cliente != null  && !cliente.getName().isEmpty())
    	   response = new Response("Done", contrato, cliente);
       else
    	   response = new Response("Done", contrato);
       return response;
   }
   
   @RequestMapping("/editar")
   public String update( @RequestParam String id,  @RequestParam String inativo, @RequestParam float vlrApolice, @RequestParam String dtInicio, 
		   @RequestParam String dtFim, @RequestParam String placa){
       Optional<Apolice> apolice = apoliceRepository.findById(id); 
       apolice.get().setVlrApolice(vlrApolice);
       apolice.get().setPlaca(placa);
       apolice.get().setInativo(inativo.equals("0") ? true : false);
       apolice.get().setDtInicio(dtInicio);
       apolice.get().setDtFim(dtFim);
       apoliceRepository.save(apolice.get());
       return "redirect:/apolices/painel";
   }
   
   public static List<Apolice> getActiveApolice(List<Apolice> _rArray) throws ParseException {
   	List<Apolice> _rContratosApolices = _rArray;
   	int days;
		for (Apolice apolice : _rContratosApolices) {
	      days = Data.contractdays(apolice.getDtFim());
		  if(days <= 0) 
			apolice.setStatus("Contrato INATIVO!");
		  else
		    apolice.setStatus("Faltam "+days+" dias p/ vencer!");
		  if(!apolice.getInativo())
			  apolice.setStatus("Contrato Reencindido!");  	  
		} 	
   	 return _rContratosApolices;
	 }
   
   public String getStatus(Boolean ativo, String dataFim) {
	  String resultString;
	   if(ativo) {
		  resultString = "Contrato Reencindido";
	  } else {
		  resultString = Data.toString(dataFim);
	  }
	   return resultString.toUpperCase();
   }
   
}
